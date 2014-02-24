/******************************************************************************
 *                       The PhenoDCC QC Database                             *
 *                                                                            *
 * DESCRIPTION:                                                               *
 * This is the database that the PhenoDCC uses for tracking quality control   *
 * of phenotyping data collected from various research centres.               *
 *                                                                            *
 * Copyright (c) 2012, Medical Research Council Harwell                       *
 * Written by: G. Yaikhom (g.yaikhom@har.mrc.ac.uk)                           *
 *                                                                            *
 *****************************************************************************/

drop database if exists ${db.qc};
create database ${db.qc};
grant all on ${db.qc}.* to 'dccadmin'@'localhost';
flush privileges;
use ${db.qc};

/**
 * A data context can be in one of the following states. This is used to
 * notify the user appropriately.
 */
drop table if exists a_state;
create table a_state (
       id tinyint unsigned not null auto_increment,
       cid tinyint unsigned not null, /* consistent identifier used by apps. */
       short_name varchar(32) not null, /* the short name for this state */
       description varchar(255) not null, /* what does the state mean? */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       primary key (id),
       unique(cid)
) engine = innodb;

/**
 * Note that the value of 'cid' is also the precedence for each state. Hence,
 * when multiple records have the same state, the state with the highest
 * 'cid' value will take precedence.
 */
insert into a_state(cid, short_name, description)
    values
    (0, 'nodata', 'No measurements for the data context'),
    (1, 'qcdone', 'QC has been completed for this data context'),
    (2, 'dataadded', 'New measurements were added for this data context'),
    (3, 'datachanged', 'Measurements were updated for this data context'),
    (4, 'dataremoved', 'Measurements were removed for this data context'),
    (5, 'hasissues', 'The data context has pending QC issues');

/**
 * An issue is a note of possible error that has been detected in a given data
 * set. Hence, an issue is only relevant to a specific data set. The data set
 * is defined by the higher-level specifiers (centre, pipeline, genotype, strain,
 * phenotyping procedure and the corresponding parameters), and a selection of
 * data points within this high-level data set. This allows a user to pin-point
 * the data points that are causing the alert.
 */
drop table if exists data_context;
create table data_context (
       id bigint unsigned not null auto_increment,
       cid int unsigned not null, /* centre id */
       lid int unsigned not null, /* pipeline id */
       gid int unsigned not null, /* genotype id */
       sid int unsigned not null, /* strain id */
       pid int unsigned not null, /* procedure id */
       qid int unsigned not null,  /* parameter id */
       state_id tinyint unsigned not null default 0, /* the new state after change */
       checksum varchar(32) not null, /* measurements checksum */
       num_measurements bigint unsigned not null default 0, /* number of measurements */
       num_issues int unsigned not null default 0, /* number of pending issues */
       num_resolved int unsigned not null default 0, /* number of resolved issues */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       touched tinyint not null default 1, /* 1 if context is still valid */
       primary key (id),
       unique data_context (cid, lid, gid, sid, pid, qid),
       foreign key (state_id) references a_state(id) on update cascade on delete restrict
) engine = innodb;

/**
 * An issue can be in various states. For instance, pending, resolved, closed,
 * assigned, etc. This table will list all of the applicable issue status.
 */
drop table if exists issue_status;
create table issue_status (
       id tinyint unsigned not null auto_increment,
       cid tinyint unsigned not null, /* consistent id */
       short_name varchar(64) not null, /* the short name we will use for the status */
       description text, /* a brief description of the status */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       index (short_name),
       primary key (id),
       unique (short_name)
) engine = innodb;

insert into issue_status(cid, short_name, description)
    values
    (0, 'new', 'A new issue'),
    (1, 'accepted', 'A user has accepted the issue and will try to resolve it'),
    (2, 'started', 'Assigned user has started to fix the issue'),
    (3, 'reassigned', 'Issue has been reassigned to another user'),
    (4, 'resolved', 'Issue has been resolved'),
    (5, 'nofix', 'There is no way to fix the issue'),
    (6, 'dataadded', 'New measurements were added since issue was raised'),
    (7, 'dataremoved', 'Measurements were removed since issue was raised'),
    (8, 'datachanged', 'Measurements were updated since issue was raised');

/**
 * Since an issue can have multiple actions (comments, actions, contribution
 * from other users etc.), we do not maintain these details in the 'an_issue'
 * table. Instead, we store these in a separate table. In the 'an_issue' table,
 * we only keep information that are common to all of the actions.
 *
 * As we can see, we note here the data context so that data points relevant to
 * any intereaction concerning this issue can be retrieved and referenced. By
 * storing the data context separately, we minimse the table size, while also
 * abstracting details that are likely to be replicated if not abstracted. Note
 * here that several issues may belong to a given data context; and hence, if
 * they are stored with the issue (as fields), these will be replicated
 * unnecessarily. Furthermore, finding all of the issues that are related to a
 * given data context is faster because we only need to check the issues against
 * only one identifier as compared to six.
 */
drop table if exists an_issue;
create table an_issue (
       id bigint unsigned not null auto_increment,
       context_id bigint unsigned not null, /* the data context id */
       title varchar(256) not null, /* the subject title of the issue */
       priority tinyint unsigned not null, /* the priority for resolution */
       status tinyint unsigned not null, /* the status of the issue */
       raised_by int unsigned not null, /* who raised the issue */
       assigned_to int unsigned not null, /* who is resolving the issue */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       index (context_id),
       index (title),
       index (priority),
       index (status),
       index (raised_by),
       index (assigned_to),
       index (last_update),
       primary key (id),
       foreign key (context_id) references data_context(id) on update cascade on delete restrict,
       foreign key (status) references issue_status(id) on update cascade on delete restrict
) engine = innodb;

/**
 * An issue is defined by the interactions that are carried out during the
 * life time of the issue. Each of these interactions are referred to as an
 * 'action'. For instance, the first action is the creation of the issue, and
 * the last action is when the issue is closed. Since there is a limited set of
 * interactions that are applicable to an issue, every action is associated
 * with an action type.
 */
drop table if exists action_type;
create table action_type (
       id tinyint unsigned not null auto_increment,
       cid tinyint unsigned not null, /* consistent id */
       short_name varchar(64) not null, /* the short name we will use for the action */
       description text, /* a brief description of the action (what it changes etc.) */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       index (short_name),
       primary key (id),
       unique (short_name)
) engine = innodb;

insert into action_type(cid, short_name, description)
    values
    (0, 'raise', 'Raise an issue'),
    (1, 'comment', 'Comment on an existing issue'),
    (2, 'accept', 'User has accepted assigment to fix the issue'),
    (3, 'start', 'Started work on fixing the issue'),
    (4, 'resolve', 'Resolve an issue'),
    (5, 'reassign', 'Reassign issue to another user'),
    (6, 'nofix', 'Mark issue as unresolvable'),
    (7, 'adddata', 'New measurements were added'),
    (8, 'removedata', 'Measurements were removed'),
    (9, 'changedata', 'Measurements were updated'),
    (10, 'qcdone', 'No more QC issues with the data context');

/**
 * Every action is defined by what action was carried out, who carried out the
 * action and when, and to what issue.
 */
drop table if exists an_action;
create table an_action (
       id bigint unsigned not null auto_increment,
       issue_id bigint unsigned not null, /* the parent issue */
       action_type tinyint unsigned not null, /* the type of the interaction */
       description text, /* details about the action */
       actioned_by int unsigned not null, /* who created this action */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       index (issue_id),
       index (action_type),
       index (actioned_by),
       index (last_update),
       primary key (id),
       foreign key (issue_id) references an_issue(id) on update cascade on delete cascade,
       foreign key (action_type) references action_type(id) on update cascade on delete restrict
) engine = innodb;

/**
 * When databases changes affects a data context, we must record what, when,
 * who, and new state for future reference. The following table stores the
 * data context history. Note that user id -1 is assigned to changes made by
 * the crawler.
 */
drop table if exists history;
create table history (
       id bigint unsigned not null auto_increment,
       context_id bigint unsigned not null, /* context identifier */
       state_id tinyint unsigned not null, /* the new state after change */
       actioned_by int not null, /* who made the change? */
       action_type tinyint unsigned not null, /* what type of change? */
       issue_id bigint unsigned, /* if action is related to an issue */
       action_id bigint unsigned, /* associated action */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       primary key (id),
       index (context_id),
       foreign key (context_id) references data_context(id) on update cascade on delete restrict,
       foreign key (state_id) references a_state(id) on update cascade on delete restrict,
       foreign key (action_id) references an_action(id) on update cascade on delete restrict,
       foreign key (action_type) references action_type(id) on update cascade on delete restrict,
       foreign key (issue_id) references an_issue(id) on update cascade on delete restrict
) engine = innodb;

/**
 * Finally, since an issue makes sense only in reference to a set of data points,
 * we store the relevant measurements as a set of indices within the data
 * context defined by the higher-level (centre, pipeline, genotype, strain,
 * phenotyping procedure and the corresponding parameter).
 *
 * We only allow association of data points with issues and not with actions.
 * The rationale is that all actions carried out by a user, such as posting
 * comments, or resolution of issues, should remain relevant to the issue. If
 * cited data points are associated with actions, than an issue can have
 * multiple sets of data points which is very likely to confuse a discussion.
 * Furthermore, this prevents the discussions from diverging into unrelated
 * topics. If a user notices subset issues that concern data points that are
 * part of an existing issue, they should raise a new issue by selecting the
 * subset of data points. Now each concern can be treated separately.
 *
 * NOTE: The following assumes that all of the data points in a given data
 * context are uniquenly identified by the measurement identifier. This is sent
 * by the Quality Control web application.
 *
 * Notice further that measurement identifiers are treated here as any integer,
 * instead as a foregin key, although it is expected to be a primary key in
 * the measurements table of the DCC database. This is to avoid maintenance
 * of cross-database consistency. The Quality Control web application is expected
 * to resolve the consistency on the fly.
 */
drop table if exists cited_data_point;
create table cited_data_point (
       id bigint unsigned not null auto_increment,
       issue_id bigint unsigned not null, /* the issue associated with the data point */
       measurement_id bigint unsigned not null, /* the measurement identifier */
       animal_id int unsigned not null, /* animal that corresponds to measured value */
       last_update timestamp not null default current_timestamp on update current_timestamp,
       index (issue_id),
       index (measurement_id),
       primary key (id),
       foreign key (issue_id) references an_issue(id) on update cascade on delete cascade
) engine = innodb;

/* End of MySQL script */
