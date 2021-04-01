package de.dennismaas.osbdemo.servicebroker.service;

public class PlaceServiceInstance {

    private String instanceId;
    private String serviceDefinitionId;
    private String planId;
    private String dashboardUrl;

    public PlaceServiceInstance(String instanceId, String serviceDefinitionId, String planId, String dashboardUrl) {
        this.instanceId = instanceId;
        this.serviceDefinitionId = serviceDefinitionId;
        this.planId = planId;
        this.dashboardUrl = dashboardUrl;
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


