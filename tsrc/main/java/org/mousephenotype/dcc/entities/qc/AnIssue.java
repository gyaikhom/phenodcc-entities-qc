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
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gagarine Yaikhom <g.yaikhom@har.mrc.ac.uk>
 */
@Entity
@Table(name = "an_issue", catalog = "PHENODCC_QC_DATABASE_NAME", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnIssue.findAll", query = "SELECT a FROM AnIssue a WHERE a.isDeleted = 0 ORDER BY a.lastUpdate DESC"),
    @NamedQuery(name = "AnIssue.findById", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.id = :id)"),
    @NamedQuery(name = "AnIssue.findByTitle", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.title = :title)"),
    @NamedQuery(name = "AnIssue.findByPriority", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.priority = :priority) ORDER BY a.lastUpdate DESC"),
    @NamedQuery(name = "AnIssue.findByLastUpdate", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.lastUpdate = :lastUpdate)"),
    @NamedQuery(name = "AnIssue.findByContextId", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.contextId.id = :contextId) ORDER BY a.lastUpdate DESC"),
    @NamedQuery(name = "AnIssue.findByAssignedTo", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.assignedTo = :assignedTo) ORDER BY a.lastUpdate DESC"),
    @NamedQuery(name = "AnIssue.findByRaisedBy", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.raisedBy = :raisedBy) ORDER BY a.lastUpdate DESC"),
    @NamedQuery(name = "AnIssue.findByStatus", query = "SELECT a FROM AnIssue a WHERE (a.isDeleted = 0 AND a.status = :status) ORDER BY a.lastUpdate DESC"),
    @NamedQuery(name = "AnIssue.findByCentreId", query = "SELECT a FROM AnIssue a, DataContext d WHERE (a.isDeleted = 0 AND a.contextId = d AND d.cid = :centreId) ORDER BY a.lastUpdate DESC")
})
public class AnIssue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(nullable = false, length = 256)
    private String title;
    @Basic(optional = false)
    @Column(nullable = false)
    private short priority;
    @Basic(optional = false)
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Basic(optional = false)
    @Column(name = "raised_by", nullable = false)
    private Integer raisedBy;
    @Basic(optional = false)
    @Column(name = "assigned_to", nullable = false)
    private Integer assignedTo;
    @JoinColumn(name = "status", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private IssueStatus status;
    @JoinColumn(name = "context_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private DataContext contextId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issueId")
    private Collection<AnAction> anActionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issueId")
    private Collection<CitedDataPoint> citedDataPointsCollection;
    @Basic(optional = false)
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;

    public AnIssue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getPriority() {
        return priority;
    }

    public String getPriorityString() {
        String returnValue;
        switch (priority) {
            case 2:
                returnValue = "Medium";
                break;
            case 3:
                returnValue = "High";
                break;
            case 1:
            default:
                returnValue = "Low";
        }
        return returnValue;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(Integer raisedBy) {
        this.raisedBy = raisedBy;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public DataContext getContextId() {
        return contextId;
    }

    public void setContextId(DataContext contextId) {
        this.contextId = contextId;
    }

    @XmlTransient
    public Collection<AnAction> getAnActionCollection() {
        return anActionCollection;
    }

    public void setAnActionCollection(Collection<AnAction> anActionCollection) {
        this.anActionCollection = anActionCollection;
    }

    @XmlTransient
    public Collection<CitedDataPoint> getCitedDataPointsCollection() {
        return citedDataPointsCollection;
    }

    public void setCitedDataPointsCollection(Collection<CitedDataPoint> citedDataPointsCollection) {
        this.citedDataPointsCollection = citedDataPointsCollection;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
