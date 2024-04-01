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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author phuan
 */
@Entity
@Table(name = "verification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Verification.findAll", query = "SELECT v FROM Verification v"),
    @NamedQuery(name = "Verification.findById", query = "SELECT v FROM Verification v WHERE v.id = :id"),
    @NamedQuery(name = "Verification.findByOtpCode", query = "SELECT v FROM Verification v WHERE v.otpCode = :otpCode"),
    @NamedQuery(name = "Verification.findByPropersion", query = "SELECT v FROM Verification v WHERE v.propersion = :propersion"),
    @NamedQuery(name = "Verification.findByGeneratedTime", query = "SELECT v FROM Verification v WHERE v.generatedTime = :generatedTime")})
public class Verification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 45)
    @Column(name = "OTP_CODE")
    private String otpCode;
    @Size(max = 100)
    @Column(name = "PROPERSION")
    private String propersion;
    @Column(name = "GENERATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedTime;

    public Verification() {
    }

    public Verification(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getPropersion() {
        return propersion;
    }

    public void setPropersion(String propersion) {
        this.propersion = propersion;
    }

    public Date getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(Date generatedTime) {
        this.generatedTime = generatedTime;
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
        if (!(object instanceof Verification)) {
            return false;
        }
        Verification other = (Verification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.annp.pojo.Verification[ id=" + id + " ]";
    }
    
}
