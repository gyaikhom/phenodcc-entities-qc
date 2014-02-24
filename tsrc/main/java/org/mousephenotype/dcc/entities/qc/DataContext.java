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
@Table(name = "data_context", catalog = "PHENODCC_QC_DATABASE_NAME", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataContext.findAll", query = "SELECT d FROM DataContext d"),
    @NamedQuery(name = "DataContext.findById", query = "SELECT d FROM DataContext d WHERE d.id = :id"),
    @NamedQuery(name = "DataContext.findByCid", query = "SELECT d FROM DataContext d WHERE d.cid = :cid"),
    @NamedQuery(name = "DataContext.findByLid", query = "SELECT d FROM DataContext d WHERE d.lid = :lid"),
    @NamedQuery(name = "DataContext.findByGid", query = "SELECT d FROM DataContext d WHERE d.gid = :gid"),
    @NamedQuery(name = "DataContext.findBySid", query = "SELECT d FROM DataContext d WHERE d.sid = :sid"),
    @NamedQuery(name = "DataContext.findByPid", query = "SELECT d FROM DataContext d WHERE d.pid = :pid"),
    @NamedQuery(name = "DataContext.findByQid", query = "SELECT d FROM DataContext d WHERE d.qid = :qid"),
    @NamedQuery(name = "DataContext.findProcedureState", query = "SELECT new org.mousephenotype.dcc.entities.qc.StateAndUnresolvedIssuesCount(MAX(d.stateId.cid), SUM(d.numIssues - d.numResolved)) FROM DataContext d, Parameter p WHERE (d.cid = :centreId AND d.lid = :pipelineId AND d.gid = :genotypeId AND d.sid = :strainId AND d.pid = :procedureId AND d.qid = p.parameterId AND p.isMeta = 0 AND p.graphType IS NOT NULL) GROUP BY d.pid"),
    @NamedQuery(name = "DataContext.findParameterState", query = "SELECT new org.mousephenotype.dcc.entities.qc.StateAndUnresolvedIssuesCount(MAX(d.stateId.cid), SUM(d.numIssues - d.numResolved)) FROM DataContext d WHERE (d.cid = :centreId AND d.lid = :pipelineId AND d.gid = :genotypeId AND d.sid = :strainId AND d.pid = :procedureId AND d.qid = :parameterId) GROUP BY d.qid"),
    @NamedQuery(name = "DataContext.getStatusAndCountQcIssues", query = "SELECT new org.mousephenotype.dcc.entities.qc.StateAndUnresolvedIssuesCount(d.stateId.cid, (d.numIssues - d.numResolved)) FROM DataContext d, Parameter p WHERE (d.cid = :centreId AND d.lid = :pipelineId AND d.gid = :genotypeId AND d.sid = :strainId AND d.qid = p.parameterId AND p.parameterKey = :parameterId)"),
    @NamedQuery(name = "DataContext.getWithMeasurements", query = "SELECT c from DataContext c WHERE (c.numMeasurements > 0)"),
    @NamedQuery(name = "DataContext.findByCidLidGid", query = "SELECT d FROM DataContext d WHERE (d.cid = :cid AND d.lid = :lid AND d.gid = :gid)"),
    @NamedQuery(name = "DataContext.findByCidLidGidSid", query = "SELECT d FROM DataContext d WHERE (d.cid = :cid AND d.lid = :lid AND d.gid = :gid AND d.sid = :sid)"),
    @NamedQuery(name = "DataContext.findByCidLidGidSidPid", query = "SELECT d FROM DataContext d WHERE (d.cid = :cid AND d.lid = :lid AND d.gid = :gid AND d.sid = :sid AND d.pid = :pid)"),
    @NamedQuery(name = "DataContext.findByCidLidGidSidPidQid", query = "SELECT d FROM DataContext d WHERE (d.cid = :cid AND d.lid = :lid AND d.gid = :gid AND d.sid = :sid AND d.pid = :pid AND d.qid = :qid)"),
    @NamedQuery(name = "DataContext.findByContext", query = "SELECT d FROM DataContext d WHERE (d.cid = :cid AND d.lid = :lid AND d.gid = :gid AND d.sid = :sid AND d.pid = :pid AND d.qid = :qid)"),
    @NamedQuery(name = "DataContext.findProceduresWithData", query = "SELECT DISTINCT d.pid FROM DataContext d WHERE (d.cid = :cid AND d.gid = :gid AND d.sid = :sid AND d.numMeasurements > 0)"),
    @NamedQuery(name = "DataContext.findProceduresWithDataForCentre", query = "SELECT DISTINCT d.pid FROM DataContext d WHERE (d.cid = :cid AND d.numMeasurements > 0)"),
    @NamedQuery(name = "DataContext.findParametersWithData", query = "SELECT DISTINCT d.qid FROM DataContext d WHERE (d.cid = :cid AND d.gid = :gid AND d.sid = :sid AND d.pid = :pid AND d.numMeasurements > 0)")
})
public class DataContext implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer cid;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer lid;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer gid;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer sid;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer pid;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer qid;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String checksum;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer touched;
    @Basic(optional = false)
    @Column(name = "num_measurements", nullable = false)
    private long numMeasurements;
    @Basic(optional = false)
    @Column(name = "num_issues", nullable = false)
    private int numIssues;
    @Basic(optional = false)
    @Column(name = "num_resolved", nullable = false)
    private int numResolved;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contextId")
    private Collection<History> historyCollection;
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private AState stateId;
    @Basic(optional = false)
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contextId")
    private Collection<AnIssue> anIssueCollection;

    public DataContext() {
    }

    public DataContext(Integer cid, Integer lid, Integer gid, Integer sid, Integer pid, Integer qid) {
        this.cid = cid;
        this.lid = lid;
        this.gid = gid;
        this.sid = sid;
        this.pid = pid;
        this.qid = qid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Integer getTouched() {
        return touched;
    }

    public void setTouched(Integer touched) {
        this.touched = touched;
    }
    
    public long getNumMeasurements() {
        return numMeasurements;
    }

    public void setNumMeasurements(long numMeasurements) {
        this.numMeasurements = numMeasurements;
    }

    public int getNumIssues() {
        return numIssues;
    }

    public void setNumIssues(int numIssues) {
        this.numIssues = numIssues;
    }

    public int getNumResolved() {
        return numResolved;
    }

    public void setNumResolved(int numResolved) {
        this.numResolved = numResolved;
    }

    @XmlTransient
    public Collection<History> getHistoryCollection() {
        return historyCollection;
    }

    public void setHistoryCollection(Collection<History> historyCollection) {
        this.historyCollection = historyCollection;
    }

    public AState getStateId() {
        return stateId;
    }

    public void setStateId(AState stateId) {
        this.stateId = stateId;
    }

    @XmlTransient
    public Collection<AnIssue> getAnIssueCollection() {
        return anIssueCollection;
    }

    public void setAnIssueCollection(Collection<AnIssue> anIssueCollection) {
        this.anIssueCollection = anIssueCollection;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
