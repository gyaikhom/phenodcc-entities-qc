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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Gagarine Yaikhom <g.yaikhom@har.mrc.ac.uk>
 */
@Entity
@XmlRootElement
@XmlType(propOrder = {"i", "u", "t", "s", "w", "r", "a"})
public class HistoryEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    private int actionedBy;
    private String user;
    private String actionType;
    private String stateAfterModification;
    @Basic(optional = false)
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    private Long issueId;
    private Long actionId;

    public HistoryEntry() {
    }

    public HistoryEntry(Long id, int actionedBy, String actionType,
            String stateAfterModification, AnAction actionId,
            AnIssue issueId, Date lastUpdate) {
        this.id = id;
        this.actionedBy = actionedBy;
        this.actionType = actionType;
        this.stateAfterModification = stateAfterModification;
        this.actionId = actionId == null ? -1 : actionId.getId();
        this.issueId = issueId == null ? -1 : issueId.getId();
        this.lastUpdate = lastUpdate;
    }

    @XmlElement(name = "i")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public int getActionedBy() {
        return actionedBy;
    }

    public void setActionedBy(int actionedBy) {
        this.actionedBy = actionedBy;
    }

    @XmlElement(name = "u")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    @XmlElement(name = "t")
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @XmlElement(name = "s")
    public String getStateAfterModification() {
        return stateAfterModification;
    }

    public void setStateAfterModification(String stateAfterModification) {
        this.stateAfterModification = stateAfterModification;
    }

    @XmlElement(name = "w")
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @XmlElement(name = "r")
    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    @XmlElement(name = "a")
    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }
}
