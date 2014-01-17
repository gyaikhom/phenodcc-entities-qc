/*
 * Copyright 2013 Medical Research Council Harwell.
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
@Table(name = "history", catalog = "PHENODCC_QC_DATABASE_NAME", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "History.findAll", query = "SELECT h FROM History h"),
    @NamedQuery(name = "History.findById", query = "SELECT h FROM History h WHERE h.id = :id"),
    @NamedQuery(name = "History.findByContextId", query = "SELECT new org.mousephenotype.dcc.entities.qc.HistoryEntry(h.id, h.actionedBy, h.actionType.shortName, h.stateId.shortName, h.actionId, h.issueId, h.lastUpdate) FROM History h WHERE h.contextId.id = :contextId ORDER BY h.lastUpdate"),
    @NamedQuery(name = "History.findByActionedBy", query = "SELECT h FROM History h WHERE h.actionedBy = :actionedBy"),
    @NamedQuery(name = "History.findByLastUpdate", query = "SELECT h FROM History h WHERE h.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "History.findByActionType", query = "SELECT h FROM History h WHERE h.actionType = :actionType")})
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "actioned_by", nullable = false)
    private int actionedBy;
    @Basic(optional = false)
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @JoinColumn(name = "action_type", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private ActionType actionType;
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private AState stateId;
    @JoinColumn(name = "context_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private DataContext contextId;
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    private AnAction actionId;
    @JoinColumn(name = "issue_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    private AnIssue issueId;
    
    public History() {
    }

    public History(DataContext contextId, int actionedBy,
            ActionType actionType, AState stateId,
            AnAction actionId, AnIssue issueId) {
        this.actionedBy = actionedBy;
        this.actionType = actionType;
        this.stateId = stateId;
        this.contextId = contextId;
        this.actionId = actionId;
        this.issueId = issueId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnAction getActionId() {
        return actionId;
    }

    public void setActionId(AnAction actionId) {
        this.actionId = actionId;
    }
    
    public int getActionedBy() {
        return actionedBy;
    }

    public void setActionedBy(int actionedBy) {
        this.actionedBy = actionedBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public AState getStateId() {
        return stateId;
    }

    public void setStateId(AState stateId) {
        this.stateId = stateId;
    }

    public DataContext getContextId() {
        return contextId;
    }

    public void setContextId(DataContext contextId) {
        this.contextId = contextId;
    }

    public AnIssue getIssueId() {
        return issueId;
    }

    public void setIssueId(AnIssue issueId) {
        this.issueId = issueId;
    }
}
