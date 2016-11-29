package com.triad.dataobject;

import java.io.Serializable;

public class Query implements Serializable {

    private static final long serialVersionUID = 294961207010028939L;

    /**
     * the query sentences
     */
    private String request;

    /**
     * the query result
     */
    private String response;

    /**
     * the master address
     */
    private String master;

    /**
     * the port for query request
     */
    private Integer port;

    /**
     * The default LUBM Query
     */
    private String lubmQuery;


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLubmQuery() {
        return lubmQuery;
    }

    public void setLubmQuery(String lubmQuery) {
        this.lubmQuery = lubmQuery;
    }
}
