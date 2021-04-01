package de.dennismaas.osbdemo.servicebroker.service;


import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class PlaceServiceInstanceService implements ServiceInstanceService {

    private final OsbService osbService;

    public PlaceServiceInstanceService(OsbService osbService) {
        this.osbService = osbService;
    }

    @Override
    public Mono<CreateServiceInstanceResponse> createServiceInstance(CreateServiceInstanceRequest request) {
        return Mono.just(request.getServiceInstanceId())
                .flatMap(instanceId -> Mono.just(CreateServiceInstanceResponse.builder())
                        .flatMap(responseBuilder -> osbService.serviceInstanceExists(instanceId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return osbService.getServiceInstance(instanceId)
                                                .flatMap(mailServiceInstance -> Mono.just(responseBuilder
                                                        .instanceExisted(true)
                                                        .dashboardUrl(mailServiceInstance.getDashboardUrl())
                                                        .build()));
                                    } else {
                                        return osbService.createServiceInstance(
                                                instanceId, request.getServiceDefinitionId(), request.getPlanId())
                                                .flatMap(mailServiceInstance -> Mono.just(responseBuilder
                                                        .instanceExisted(false)
                                                        .dashboardUrl(mailServiceInstance.getDashboardUrl())
                                                        .build()));
                                    }
                                })));
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