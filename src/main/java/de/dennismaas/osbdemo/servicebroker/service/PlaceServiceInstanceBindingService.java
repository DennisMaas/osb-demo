package de.dennismaas.osbdemo.servicebroker.service;

import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.cloud.servicebroker.model.binding.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class PlaceServiceInstanceBindingService implements ServiceInstanceBindingService {

    private final OsbService osbService;

    public PlaceServiceInstanceBindingService(OsbService osbService) {
        this.osbService = osbService;
    }

    @Override
    public Mono<CreateServiceInstanceBindingResponse> createServiceInstanceBinding(
            CreateServiceInstanceBindingRequest request) {
        return Mono.just(CreateServiceInstanceAppBindingResponse.builder())
                .flatMap(responseBuilder -> osbService.serviceBindingExists(
                        request.getServiceInstanceId(), request.getBindingId())
                        .flatMap(exists -> {
                            if (exists) {
                                return osbService.getServiceBinding(
                                        request.getServiceInstanceId(), request.getBindingId())
                                        .flatMap(serviceBinding -> Mono.just(responseBuilder
                                                .bindingExisted(true)
                                                .credentials(serviceBinding.getCredentials())
                                                .build()));
                            } else {
                                return osbService.createServiceBinding(
                                        request.getServiceInstanceId(), request.getBindingId())
                                        .switchIfEmpty(Mono.error(
                                                new ServiceInstanceDoesNotExistException(
                                                        request.getServiceInstanceId())))
                                        .flatMap(placeServiceBinding -> Mono.just(responseBuilder
                                                .bindingExisted(false)
                                                .credentials(placeServiceBinding.getCredentials())
                                                .build()));
                            }
                        }));
    }

    @Override
    public Mono<GetServiceInstanceBindingResponse> getServiceInstanceBinding(GetServiceInstanceBindingRequest request) {
        return osbService.getServiceBinding(request.getServiceInstanceId(), request.getBindingId())
                .switchIfEmpty(Mono.error(new ServiceInstanceBindingDoesNotExistException(request.getBindingId())))
                .flatMap(placeServiceBinding -> Mono.just(GetServiceInstanceAppBindingResponse.builder()
                        .credentials(placeServiceBinding.getCredentials())
                        .build()));
    }

    @Override
    public Mono<DeleteServiceInstanceBindingResponse> deleteServiceInstanceBinding(
            DeleteServiceInstanceBindingRequest request) {
        return osbService.serviceBindingExists(request.getServiceInstanceId(), request.getBindingId())
                .flatMap(exists -> {
                    if (exists) {
                        return osbService.deleteServiceBinding(request.getServiceInstanceId())
                                .thenReturn(DeleteServiceInstanceBindingResponse.builder().build());
                    } else {
                        return Mono.error(new ServiceInstanceBindingDoesNotExistException(request.getBindingId()));
                    }
                });
    }
}
