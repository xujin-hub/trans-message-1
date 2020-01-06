package com.finest.app.pramas;

import java.util.Map;

public class Param {

    private Integer forward;
    private String senderID;
    private String serviceID;
    private String serviceAlias;
    private Descript serviceDescript;


    public Param(Integer  forward,String senderID, String serviceID, String serviceAlias, Descript serviceDescript) {
        this.forward = forward;
        this.senderID = senderID;
        this.serviceID = serviceID;
        this.serviceAlias = serviceAlias;
        this.serviceDescript = serviceDescript;
    }

    public Param(Integer forward,String senderID, String serviceID, String serviceAlias) {
        this.forward = forward;
        this.senderID = senderID;
        this.serviceID = serviceID;
        this.serviceAlias = serviceAlias;
    }

    public Param() {
    }

    public Descript descriptBuilder(Map<String, String> header,Object data,String serviceName,String serviceMethod){
        Descript descript = new Descript();
        descript.setHeader(header);
        descript.setData(data);
        descript.setServiceName(serviceName);
        descript.setServiceMethod(serviceMethod);
        this.serviceDescript = descript;
        return descript;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceAlias() {
        return serviceAlias;
    }

    public void setServiceAlias(String serviceAlias) {
        this.serviceAlias = serviceAlias;
    }

    public Descript getServiceDescript() {
        return serviceDescript;
    }

    public void setServiceDescript(Descript serviceDescript) {
        this.serviceDescript = serviceDescript;
    }

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

}


