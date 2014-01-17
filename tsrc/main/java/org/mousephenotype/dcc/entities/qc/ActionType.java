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
import javax.persistence.Lob;
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
@Table(name = "action_type", catalog = "PHENODCC_QC_DATABASE_NAME", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"short_name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActionType.findAll", query = "SELECT a FROM ActionType a"),
    @NamedQuery(name = "ActionType.findById", query = "SELECT a FROM ActionType a WHERE a.id = :id"),
    @NamedQuery(name = "ActionType.findByCid", query = "SELECT a FROM ActionType a WHERE a.cid = :cid"),
    @NamedQuery(name = "ActionType.findByShortName", query = "SELECT a FROM ActionType a WHERE a.shortName = :shortName"),
    @NamedQuery(name = "ActionType.findByLastUpdate", query = "SELECT a FROM ActionType a WHERE a.lastUpdate = :lastUpdate")})
public class ActionType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short id;
    
    /* consistent id - value is database independent
     * NOTE: we must not use the database assigned id (above) as it depends on
     * the autoincrement setting, which, for clusters could have gaps. It is
     * necessary that these identifiers are sequential and contiguous.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private Short cid;

    @Basic(optional = false)
    @Column(name = "short_name", nullable = false, length = 64)
    private String shortName;
    @Lob
    @Column(length = 65535)
    private String description;
    @Basic(optional = false)
    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionType")
    private Collection<AnAction> anActionCollection;

    public ActionType() {
    }

    public ActionType(Short cid, String shortName) {
        this.cid = cid;
        this.shortName = shortName;
    }

    public ActionType(Short cid, String shortName, String description) {
        this.cid = cid;
        this.shortName = shortName;
        this.description = description;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getCid() {
        return cid;
    }

    public void setCid(Short cid) {
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
    public Collection<AnAction> getAnActionCollection() {
        return anActionCollection;
    }

    public void setAnActionCollection(Collection<AnAction> anActionCollection) {
        this.anActionCollection = anActionCollection;
    }
}
