package de.dennismaas.osbdemo.servicebroker.service;

public class PlaceServiceInstance {

    private String instanceId;
    private String serviceDefinitionId;
    private String planId;
    private String dashboardUrl;

    public PlaceServiceInstance(String instanceId, String serviceDefinitionId, String planId) {
        this.instanceId = instanceId;
        this.serviceDefinitionId = serviceDefinitionId;
        this.planId = planId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getServiceDefinitionId() {
        return serviceDefinitionId;
    }

    public String getPlanId() {
        return planId;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }
}


