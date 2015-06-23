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
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gagarine Yaikhom <g.yaikhom@har.mrc.ac.uk>
 */
@Entity
@Table(name = "a_state", catalog = "PHENODCC_QC_DATABASE_NAME", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AState.findAll", query = "SELECT a FROM AState a"),
    @NamedQuery(name = "AState.findById", query = "SELECT a FROM AState a WHERE a.id = :id"),
    @NamedQuery(name = "AState.findByCid", query = "SELECT a FROM AState a WHERE a.cid = :cid"),
    @NamedQuery(name = "AState.findByShortName", query = "SELECT a FROM AState a WHERE a.shortName = :shortName"),
    @NamedQuery(name = "AState.findByDescription", query = "SELECT a FROM AState a WHERE a.description = :description"),
    @NamedQuery(name = "AState.findByLastUpdate", query = "SELECT a FROM AState a WHERE a.lastUpdate = :lastUpdate")})
public class AState implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short id;
    @Basic(optional = false)
    @Column(nullable = false)
    private short cid;
    @Basic(optional = false)
    @Column(name = "short_name", nullable = false, length = 32)
    private String shortName;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String description;
    @Basic(optional = false)
    @Column(name = "last_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateId")
    private Collection<History> historyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateId")
    private Collection<DataContext> dataContextCollection;

    public AState() {
    }

    public AState(Short id) {
        this.id = id;
    }

    public AState(
            Short id,
            short cid,
            String shortName,
            String description,
            Date lastUpdate) {
        this.id = id;
        this.cid = cid;
        this.shortName = shortName;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public short getCid() {
        return cid;
    }

    public void setCid(short cid) {
        this.cid = cid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    @XmlTransient
    public Collection<History> getHistoryCollection() {
        return historyCollection;
    }

    public void setHistoryCollection(Collection<History> historyCollection) {
        this.historyCollection = historyCollection;
    }

    @XmlTransient
    public Collection<DataContext> getDataContextCollection() {
        return dataContextCollection;
    }

    public void setDataContextCollection(Collection<DataContext> dataContextCollection) {
        this.dataContextCollection = dataContextCollection;
    }
}
