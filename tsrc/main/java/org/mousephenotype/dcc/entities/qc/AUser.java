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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gagarine Yaikhom <g.yaikhom@har.mrc.ac.uk>
 */
@Entity
@Table(name = "users", catalog = "DRUPAL_DATABASE_NAME", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AUser.findAll", query = "SELECT u FROM AUser u"),
    @NamedQuery(name = "AUser.findByUid", query = "SELECT u FROM AUser u WHERE u.uid = :uid"),
    @NamedQuery(name = "AUser.findByName", query = "SELECT u FROM AUser u WHERE u.name = :name"),
    @NamedQuery(name = "AUser.findByPass", query = "SELECT u FROM AUser u WHERE u.pass = :pass"),
    @NamedQuery(name = "AUser.findByMail", query = "SELECT u FROM AUser u WHERE u.mail = :mail"),
    @NamedQuery(name = "AUser.findByTheme", query = "SELECT u FROM AUser u WHERE u.theme = :theme"),
    @NamedQuery(name = "AUser.findBySignature", query = "SELECT u FROM AUser u WHERE u.signature = :signature"),
    @NamedQuery(name = "AUser.findBySignatureFormat", query = "SELECT u FROM AUser u WHERE u.signatureFormat = :signatureFormat"),
    @NamedQuery(name = "AUser.findByCreated", query = "SELECT u FROM AUser u WHERE u.created = :created"),
    @NamedQuery(name = "AUser.findByAccess", query = "SELECT u FROM AUser u WHERE u.access = :access"),
    @NamedQuery(name = "AUser.findByLogin", query = "SELECT u FROM AUser u WHERE u.login = :login"),
    @NamedQuery(name = "AUser.findByStatus", query = "SELECT u FROM AUser u WHERE u.status = :status"),
    @NamedQuery(name = "AUser.findByTimezone", query = "SELECT u FROM AUser u WHERE u.timezone = :timezone"),
    @NamedQuery(name = "AUser.findByLanguage", query = "SELECT u FROM AUser u WHERE u.language = :language"),
    @NamedQuery(name = "AUser.findByPicture", query = "SELECT u FROM AUser u WHERE u.picture = :picture"),
    @NamedQuery(name = "AUser.findByInit", query = "SELECT u FROM AUser u WHERE u.init = :init")})
public class AUser implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer uid;
    @Basic(optional = false)
    @Column(nullable = false, length = 60)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false, length = 128)
    private String pass;
    @Column(length = 254)
    private String mail;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String theme;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String signature;
    @Column(name = "signature_format", length = 255)
    private String signatureFormat;
    @Basic(optional = false)
    @Column(nullable = false)
    private int created;
    @Basic(optional = false)
    @Column(nullable = false)
    private int access;
    @Basic(optional = false)
    @Column(nullable = false)
    private int login;
    @Basic(optional = false)
    @Column(nullable = false)
    private short status;
    @Column(length = 32)
    private String timezone;
    @Basic(optional = false)
    @Column(nullable = false, length = 12)
    private String language;
    @Basic(optional = false)
    @Column(nullable = false)
    private int picture;
    @Column(length = 254)
    private String init;
    @Lob
    private byte[] data;

    public AUser() {
    }

    public AUser(Integer uid) {
        this.uid = uid;
    }

    public AUser(Integer uid, String name, String pass, String theme, String signature, int created, int access, int login, short status, String language, int picture) {
        this.uid = uid;
        this.name = name;
        this.pass = pass;
        this.theme = theme;
        this.signature = signature;
        this.created = created;
        this.access = access;
        this.login = login;
        this.status = status;
        this.language = language;
        this.picture = picture;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureFormat() {
        return signatureFormat;
    }

    public void setSignatureFormat(String signatureFormat) {
        this.signatureFormat = signatureFormat;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return name;
    }
}
