package com.annp.dto;

public class GeoIPDto {

    private String location;
    private String isp;

    public GeoIPDto(String location, String isp) {
        this.location = location;
        this.isp = isp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
