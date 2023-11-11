package com.saeta.saetasigner.model;

public class ResponseProcesoFirmaEntity {
    private String message;
    private ConfigResponse config;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ConfigResponse getConfig() {
        return config;
    }

    public void setConfig(ConfigResponse config) {
        this.config = config;
    }
}
