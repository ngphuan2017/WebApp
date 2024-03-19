/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.pojo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author phuan
 */
@Entity
@Table(name = "user_levels")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserLevels.findAll", query = "SELECT u FROM UserLevels u"),
    @NamedQuery(name = "UserLevels.findById", query = "SELECT u FROM UserLevels u WHERE u.id = :id"),
    @NamedQuery(name = "UserLevels.findByLevelName", query = "SELECT u FROM UserLevels u WHERE u.levelName = :levelName"),
    @NamedQuery(name = "UserLevels.findByRequiredExp", query = "SELECT u FROM UserLevels u WHERE u.requiredExp = :requiredExp")})
public class UserLevels implements Serializable {

    @Size(max = 500)
    @Column(name = "rank_img")
    private String rankImg;
    @Size(max = 7)
    @Column(name = "rank_color")
    private String rankColor;

    @Size(max = 50)
    @Column(name = "user_rank")
    private String userRank;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "level_name")
    private String levelName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "required_exp")
    private int requiredExp;

    public UserLevels() {
    }

    public UserLevels(Integer id) {
        this.id = id;
    }

    public UserLevels(Integer id, String levelName, int requiredExp) {
        this.id = id;
        this.levelName = levelName;
        this.requiredExp = requiredExp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getRequiredExp() {
        return requiredExp;
    }

    public void setRequiredExp(int requiredExp) {
        this.requiredExp = requiredExp;
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
        if (!(object instanceof UserLevels)) {
            return false;
        }
        UserLevels other = (UserLevels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.annp.pojo.UserLevels[ id=" + id + " ]";
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public String getRankImg() {
        return rankImg;
    }

    public void setRankImg(String rankImg) {
        this.rankImg = rankImg;
    }

    public String getRankColor() {
        return rankColor;
    }

    public void setRankColor(String rankColor) {
        this.rankColor = rankColor;
    }
    
}
