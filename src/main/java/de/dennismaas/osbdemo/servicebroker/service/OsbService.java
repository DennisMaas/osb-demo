package de.dennismaas.osbdemo.servicebroker.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class OsbService {

    private Map<String, PlaceServiceInstance> placeServices = new HashMap<>();
    private Map<String, PlaceServiceBinding> placeServiceBindings = new HashMap<>();


    public Mono<Boolean> serviceInstanceExists(String instanceId) {
        return Mono.just(placeServices.containsKey(instanceId));
    }


    public Mono<PlaceServiceInstance> getServiceInstance(String instanceId) {
        if (placeServices.containsKey(instanceId)) {
            return Mono.just(placeServices.get(instanceId));
        }
        return Mono.empty();
    }

    public Mono<PlaceServiceInstance> createServiceInstance(String instanceId, String serviceDefinitionId, String planId){
        PlaceServiceInstance placeServiceInstance = new PlaceServiceInstance(instanceId, serviceDefinitionId, planId);
        placeServices.put(instanceId, placeServiceInstance);
        return Mono.just(placeServiceInstance);
    }
}
