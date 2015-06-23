/*
 * Copyright 2012 Medical Research Council Harwell.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mousephenotype.dcc.entities.qc;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gagarine Yaikhom <g.yaikhom@har.mrc.ac.uk>
 */
@Entity
@Table(name = "an_action", catalog = "PHENODCC_QC_DATABASE_NAME", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnAction.findAll", query = "SELECT a FROM AnAction a"),
    @NamedQuery(name = "AnAction.findById", query = "SELECT a FROM AnAction a WHERE a.id = :id"),
    @NamedQuery(name = "AnAction.findByLastUpdate", query = "SELECT a FROM AnAction a WHERE a.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "AnAction.findByActionedBy", query = "SELECT a FROM AnAction a WHERE a.actionedBy = :actionedBy"),
    @NamedQuery(name = "AnAction.findByActionType", query = "SELECT a FROM AnAction a WHERE a.actionType = :actionType"),
    @NamedQuery(name = "AnAction.findByIssueId", query = "SELECT a FROM AnAction a WHERE a.issueId = :issueId ORDER BY a.id DESC")})
public class AnAction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Lob
    @Column(length = 65535)
    private String description;
    @Basic(optional = false)
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Basic(optional = false)
    @Column(name = "actioned_by", nullable = false)
    private Integer actionedBy;
    @JoinColumn(name = "action_type", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private ActionType actionType;
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private AnIssue issueId;

    public AnAction() {
    }

    public AnAction(
            ActionType actionType,
            Integer actionedBy,
            String title,
            String description) {
        this.actionType = actionType;
        this.actionedBy = actionedBy;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getActionedBy() {
        return actionedBy;
    }

    public void setActionedBy(Integer actionedBy) {
        this.actionedBy = actionedBy;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public AnIssue getIssueId() {
        return issueId;
    }

    public void setIssueId(AnIssue issueId) {
        this.issueId = issueId;
    }
}
