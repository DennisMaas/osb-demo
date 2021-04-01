package de.dennismaas.osbdemo.servicebroker.service;


import de.dennismaas.osbdemo.web.service.PlaceService;
import org.springframework.cloud.servicebroker.model.instance.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
@Service
public class PlaceServiceInstanceService implements ServiceInstanceService {

    private final PlaceService placeService;

    public PlaceServiceInstanceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Override
    public Mono<CreateServiceInstanceResponse> createServiceInstance(CreateServiceInstanceRequest request) {
        String serviceInstanceId = request.getServiceInstanceId();
        String planId = request.getPlanId();
        Map<String, Object> parameters = request.getParameters();

        //
        // perform the steps necessary to initiate the asynchronous
        // provisioning of all necessary resources
        //

        String dashboardUrl = "http://localhost:8080/api/places"; /* construct a dashboard URL */

        return Mono.just(CreateServiceInstanceResponse.builder()
                .dashboardUrl(dashboardUrl)
                .async(true)
                .build());
    }


    @Override
    public Mono<DeleteServiceInstanceResponse> deleteServiceInstance(DeleteServiceInstanceRequest request) {
        String serviceInstanceId = request.getServiceInstanceId();
        String planId = request.getPlanId();

        //
        // perform the steps necessary to initiate the asynchronous
        // deletion of all provisioned resources
        //

        return Mono.just(DeleteServiceInstanceResponse.builder()
                .async(true)
                .build());
    }


}