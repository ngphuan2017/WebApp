/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.pojo;

/**
 *
 * @author phuan
 */
public class Facebook {
    private String id;
    private String name;
    private Integer gender;
    private String email;
    private com.restfb.types.NamedFacebookType location;
    private com.restfb.types.ProfilePictureSource picture;

    public Facebook(){
    
    }
    public Facebook(String id, String name, Integer gender, String email, com.restfb.types.ProfilePictureSource picture,
            com.restfb.types.NamedFacebookType location){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.location = location;
        this.picture = picture;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the gender
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return the location
     */
    public com.restfb.types.NamedFacebookType getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(com.restfb.types.NamedFacebookType location) {
        this.location = location;
    }

    /**
     * @return the picture
     */
    public com.restfb.types.ProfilePictureSource getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(com.restfb.types.ProfilePictureSource picture) {
        this.picture = picture;
    }
}
