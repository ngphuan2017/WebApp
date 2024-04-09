/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.configs;

import com.annp.dto.MoMoEndpointDto;
import com.annp.dto.PartnerInfoDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author phuan
 */
public class Environment {

    private PartnerInfoDto partnerInfo;
    private MoMoEndpointDto endpoints;
    private String target;

    public Environment(MoMoEndpointDto endpoints, PartnerInfoDto partnerInfo, EnvTarget target) {
        this(endpoints, partnerInfo, target.string());
    }

    public Environment(MoMoEndpointDto momoEndpoint, PartnerInfoDto partnerInfo, String target) {
        this.endpoints = momoEndpoint;
        this.partnerInfo = partnerInfo;
        this.target = target;
    }

    /**
     *
     * @param target String target name ("dev" or "prod")
     * @return
     * @throws IllegalArgumentException
     */
    public static Environment selectEnv(String target) throws IllegalArgumentException {
        switch (target) {
            case "dev":
                return selectEnv(EnvTarget.DEV);
            case "prod":
                return selectEnv(EnvTarget.PROD);
            default:
                throw new IllegalArgumentException("MoMo doesnt provide other environment: dev and prod");
        }
    }

    /**
     * Select appropriate environment to run processes Create and modify your
     * environment.properties file appropriately
     *
     * @param target EnvTarget (choose DEV or PROD)
     * @return
     */
    public static Environment selectEnv(EnvTarget target) {
        try (InputStream input = Environment.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            switch (target) {
                case DEV:
                    MoMoEndpointDto devEndpoint = new MoMoEndpointDto(prop.getProperty("momo.endpoint"),
                            prop.getProperty("CREATE_URL"),
                            prop.getProperty("REFUND_URL"),
                            prop.getProperty("QUERY_URL"),
                            prop.getProperty("CONFIRM_URL"),
                            prop.getProperty("TOKEN_PAY_URL"),
                            prop.getProperty("TOKEN_BIND_URL"),
                            prop.getProperty("TOKEN_INQUIRY_URL"),
                            prop.getProperty("TOKEN_DELETE_URL"));
                    PartnerInfoDto devInfo = new PartnerInfoDto(prop.getProperty("momo.api.partner.code"), prop.getProperty("momo.api.accsess.key"), prop.getProperty("momo.api.secret.key"));
                    Environment dev = new Environment(devEndpoint, devInfo, target);
                    return dev;
                case PROD:
                    MoMoEndpointDto prodEndpoint = new MoMoEndpointDto(prop.getProperty("momo.endpoint"),
                            prop.getProperty("CREATE_URL"),
                            prop.getProperty("REFUND_URL"),
                            prop.getProperty("QUERY_URL"),
                            prop.getProperty("CONFIRM_URL"),
                            prop.getProperty("TOKEN_PAY_URL"),
                            prop.getProperty("TOKEN_BIND_URL"),
                            prop.getProperty("TOKEN_INQUIRY_URL"),
                            prop.getProperty("TOKEN_DELETE_URL"));
                    PartnerInfoDto prodInfo = new PartnerInfoDto(prop.getProperty("momo.api.partner.code"), prop.getProperty("momo.api.accsess.key"), prop.getProperty("momo.api.secret.key"));
                    Environment prod = new Environment(prodEndpoint, prodInfo, target);
                    return prod;
                default:
                    throw new IllegalArgumentException("MoMo doesnt provide other environment: dev and prod");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MoMoEndpointDto getMomoEndpoint() {
        return endpoints;
    }

    public void setMomoEndpoint(MoMoEndpointDto momoEndpoint) {
        this.endpoints = momoEndpoint;
    }

    public PartnerInfoDto getPartnerInfo() {
        return partnerInfo;
    }

    public void setPartnerInfo(PartnerInfoDto partnerInfo) {
        this.partnerInfo = partnerInfo;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public enum EnvTarget {
        DEV("development"), PROD("production");

        private String target;

        EnvTarget(String target) {
            this.target = target;
        }

        public String string() {
            return this.target;
        }
    }

    public enum ProcessType {
        PAY_GATE, APP_IN_APP, PAY_POS, PAY_QUERY_STATUS, PAY_REFUND, PAY_CONFIRM;

        public String getSubDir(Properties prop) {
            return prop.getProperty(this.toString());
        }
    }

}
