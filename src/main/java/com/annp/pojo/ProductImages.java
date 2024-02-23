/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author phuan
 */
@Entity
@Table(name = "product_images")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductImages.findAll", query = "SELECT p FROM ProductImages p"),
    @NamedQuery(name = "ProductImages.findById", query = "SELECT p FROM ProductImages p WHERE p.id = :id"),
    @NamedQuery(name = "ProductImages.findByImg1", query = "SELECT p FROM ProductImages p WHERE p.img1 = :img1"),
    @NamedQuery(name = "ProductImages.findByImg2", query = "SELECT p FROM ProductImages p WHERE p.img2 = :img2"),
    @NamedQuery(name = "ProductImages.findByImg3", query = "SELECT p FROM ProductImages p WHERE p.img3 = :img3"),
    @NamedQuery(name = "ProductImages.findByCreatedDate", query = "SELECT p FROM ProductImages p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "ProductImages.findByUpdatedDate", query = "SELECT p FROM ProductImages p WHERE p.updatedDate = :updatedDate")})
public class ProductImages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 500)
    @Column(name = "IMG1")
    private String img1;
    @Size(max = 500)
    @Column(name = "IMG2")
    private String img2;
    @Size(max = 500)
    @Column(name = "IMG3")
    private String img3;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "UPDATED_BY", referencedColumnName = "ID")
    @ManyToOne
    private Users updatedBy;
    
    @Transient
    @JsonIgnore
    private MultipartFile file1;
    @Transient
    @JsonIgnore
    private MultipartFile file2;
    @Transient
    @JsonIgnore
    private MultipartFile file3;

    public ProductImages() {
    }

    public ProductImages(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    /**
     * @return the file1
     */
    @XmlTransient
    public MultipartFile getFile1() {
        return file1;
    }

    /**
     * @param file1 the file to set
     */
    public void setFile1(MultipartFile file1) {
        this.file1 = file1;
    }
    
    /**
     * @return the file2
     */
    @XmlTransient
    public MultipartFile getFile2() {
        return file2;
    }

    /**
     * @param file2 the file to set
     */
    public void setFile2(MultipartFile file2) {
        this.file2 = file2;
    }
    
    /**
     * @return the file3
     */
    @XmlTransient
    public MultipartFile getFile3() {
        return file3;
    }

    /**
     * @param file3 the file to set
     */
    public void setFile3(MultipartFile file3) {
        this.file3 = file3;
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
        if (!(object instanceof ProductImages)) {
            return false;
        }
        ProductImages other = (ProductImages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.annp.pojo.ProductImages[ id=" + id + " ]";
    }
    
}
