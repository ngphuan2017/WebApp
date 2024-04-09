/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service;

import com.annp.configs.Environment;
import com.annp.dto.PartnerInfoDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Execute;

/**
 *
 * @author phuan
 */
public abstract class AbstractProcess<T, V> {

    protected PartnerInfoDto partnerInfo;
    protected Environment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(Environment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public abstract V execute(T request) throws MoMoException;
}
