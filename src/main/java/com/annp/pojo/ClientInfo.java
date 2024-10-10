/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author phuan
 */
@Entity
@Table(name = "client_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientInfo.findAll", query = "SELECT c FROM ClientInfo c"),
    @NamedQuery(name = "ClientInfo.findById", query = "SELECT c FROM ClientInfo c WHERE c.id = :id"),
    @NamedQuery(name = "ClientInfo.findByIp", query = "SELECT c FROM ClientInfo c WHERE c.ip = :ip"),
    @NamedQuery(name = "ClientInfo.findByIsp", query = "SELECT c FROM ClientInfo c WHERE c.isp = :isp"),
    @NamedQuery(name = "ClientInfo.findByDevice", query = "SELECT c FROM ClientInfo c WHERE c.device = :device"),
    @NamedQuery(name = "ClientInfo.findByLocation", query = "SELECT c FROM ClientInfo c WHERE c.location = :location"),
    @NamedQuery(name = "ClientInfo.findByTimestamp", query = "SELECT c FROM ClientInfo c WHERE c.timestamp = :timestamp")})
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 45)
    @Column(name = "IP")
    private String ip;
    @Size(max = 45)
    @Column(name = "ISP")
    private String isp;
    @Size(max = 255)
    @Column(name = "DEVICE")
    private String device;
    @Size(max = 255)
    @Column(name = "LOCATION")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users userId;

    public ClientInfo() {
    }

    public ClientInfo(Integer id) {
        this.id = id;
    }

    public ClientInfo(Integer id, Date timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientInfo)) {
            return false;
        }
        ClientInfo other = (ClientInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.annp.pojo.ClientInfo[ id=" + id + " ]";
    }
    
}
