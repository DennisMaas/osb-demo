package de.dennismaas.osbdemo.servicebroker.service;

import java.util.Map;

public class PlaceServicebinding {

    private String bindingId;
    private Map<String, Object> credentials;

    public PlaceServicebinding(String bindingId, Map<String, Object> credentials) {
        this.bindingId = bindingId;
        this.credentials = credentials;
    }

    public String getBindingId() {
        return bindingId;
    }

    public Map<String, Object> getCredentials() {
        return credentials;
    }
}


