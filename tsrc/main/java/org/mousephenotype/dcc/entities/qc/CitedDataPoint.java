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
@Table(name = "cited_data_point", catalog = "PHENODCC_QC_DATABASE_NAME", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CitedDataPoint.findAll", query = "SELECT c FROM CitedDataPoint c"),
    @NamedQuery(name = "CitedDataPoint.findById", query = "SELECT c FROM CitedDataPoint c WHERE c.id = :id"),
    @NamedQuery(name = "CitedDataPoint.findByIssueId", query = "SELECT c FROM CitedDataPoint c WHERE c.issueId = :issueId"),
    @NamedQuery(name = "CitedDataPoint.countByIssueId", query = "SELECT COUNT(c) FROM CitedDataPoint c WHERE c.issueId = :issueId"),
    @NamedQuery(name = "CitedDataPoint.measurementsByIssueId", query = "SELECT new org.mousephenotype.dcc.entities.qc.CitedMeasurement(c.animalId, c.measurementId) FROM CitedDataPoint c WHERE c.issueId = :issueId"),
    @NamedQuery(name = "CitedDataPoint.findByMeasurementId", query = "SELECT c FROM CitedDataPoint c WHERE c.measurementId = :measurementId")})
public class CitedDataPoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "measurement_id", nullable = false)
    private long measurementId;
    @Basic(optional = false)
    @Column(name = "animal_id", nullable = false)
    private Integer animalId;
    @JoinColumn(name = "issue_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private AnIssue issueId;
    @Basic(optional = false)
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public CitedDataPoint() {
    }

    public CitedDataPoint(AnIssue issueId, long measurementId, Integer animalId) {
        this.issueId = issueId;
        this.measurementId = measurementId;
        this.animalId = animalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(long measurementId) {
        this.measurementId = measurementId;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        this.animalId = animalId;
    }

    public AnIssue getIssueId() {
        return issueId;
    }

    public void setIssueId(AnIssue issueId) {
        this.issueId = issueId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
