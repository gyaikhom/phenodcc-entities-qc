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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gagarine Yaikhom <g.yaikhom@har.mrc.ac.uk>
 */
@Entity
@Table(name = "sessions", catalog = "DRUPAL_DATABASE_NAME", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sessions.findAll", query = "SELECT s FROM Sessions s"),
    @NamedQuery(name = "Sessions.findByUid", query = "SELECT s FROM Sessions s WHERE s.uid = :uid"),
    @NamedQuery(name = "Sessions.findByUidSid", query = "SELECT s FROM Sessions s WHERE (s.uid = :uid AND s.sessionsPK.sid = :sid)"),
    @NamedQuery(name = "Sessions.findBySid", query = "SELECT s FROM Sessions s WHERE s.sessionsPK.sid = :sid"),
    @NamedQuery(name = "Sessions.findBySsid", query = "SELECT s FROM Sessions s WHERE s.sessionsPK.ssid = :ssid"),
    @NamedQuery(name = "Sessions.findByHostname", query = "SELECT s FROM Sessions s WHERE s.hostname = :hostname"),
    @NamedQuery(name = "Sessions.findByTimestamp", query = "SELECT s FROM Sessions s WHERE s.timestamp = :timestamp"),
    @NamedQuery(name = "Sessions.findByCache", query = "SELECT s FROM Sessions s WHERE s.cache = :cache")})
public class Sessions implements Serializable {

    @EmbeddedId
    protected SessionsPK sessionsPK;
    @Basic(optional = false)
    @Column(nullable = false)
    private int uid;
    @Basic(optional = false)
    @Column(nullable = false, length = 128)
    private String hostname;
    @Basic(optional = false)
    @Column(nullable = false)
    private int timestamp;
    @Basic(optional = false)
    @Column(nullable = false)
    private int cache;
    @Lob
    private byte[] session;

    public Sessions() {
    }

    public Sessions(SessionsPK sessionsPK) {
        this.sessionsPK = sessionsPK;
    }

    public Sessions(SessionsPK sessionsPK, int uid, String hostname, int timestamp, int cache) {
        this.sessionsPK = sessionsPK;
        this.uid = uid;
        this.hostname = hostname;
        this.timestamp = timestamp;
        this.cache = cache;
    }

    public Sessions(String sid, String ssid) {
        this.sessionsPK = new SessionsPK(sid, ssid);
    }

    public SessionsPK getSessionsPK() {
        return sessionsPK;
    }

    public void setSessionsPK(SessionsPK sessionsPK) {
        this.sessionsPK = sessionsPK;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public byte[] getSession() {
        return session;
    }

    public void setSession(byte[] session) {
        this.session = session;
    }
}
