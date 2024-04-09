/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.dto;

import com.annp.enums.Language;

/**
 *
 * @author phuan
 */
public class MomoRequestDto {

    private String partnerCode;
    private String requestId;
    private String orderId;
    private Language lang = Language.EN;
    private long startTime;

    public MomoRequestDto() {
        this.startTime = System.currentTimeMillis();
    }

    public MomoRequestDto(String partnerCode, String orderId, String requestId, Language lang) {
        this.partnerCode = partnerCode;
        this.orderId = orderId;
        this.requestId = requestId;
        this.lang = lang;
        this.startTime = System.currentTimeMillis();
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLang() {
        return lang.getLanguage();
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

}
