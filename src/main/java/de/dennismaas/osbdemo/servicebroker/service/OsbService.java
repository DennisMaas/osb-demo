package de.dennismaas.osbdemo.servicebroker.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OsbService {
    public static final String URI_KEY = "uri";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    private Map<String, PlaceServiceInstance> placeServices = new HashMap<>();
    private Map<String, PlaceServiceBinding> placeServiceBindings = new HashMap<>();

    public Mono<PlaceServiceInstance> createServiceInstance(String instanceId, String serviceDefinitionId, String planId) {
        PlaceServiceInstance placeServiceInstance = new PlaceServiceInstance(
                instanceId, serviceDefinitionId, planId);
        placeServices.put(instanceId, placeServiceInstance);
        return Mono.just(placeServiceInstance);
    }

    public Mono<Boolean> serviceInstanceExists(String instanceId) {
        return Mono.just(placeServices.containsKey(instanceId));
    }

    public Mono<PlaceServiceInstance> getServiceInstance(String instanceId) {
        if (placeServices.containsKey(instanceId)) {
            return Mono.just(placeServices.get(instanceId));
        }
        return Mono.empty();
    }

    public Mono<Void> deleteServiceInstance(String instanceId) {
        placeServices.remove(instanceId);
        placeServiceBindings.remove(instanceId);
        return Mono.empty();
    }

    public Mono<PlaceServiceBinding> createServiceBinding(String instanceId, String bindingId) {
        return this.serviceInstanceExists(instanceId)
                .flatMap(exists -> {
                    if (exists) {
                        PlaceServiceBinding placeServiceBinding =
                                new PlaceServiceBinding(bindingId, buildCredentials(instanceId, bindingId));
                        placeServiceBindings.put(instanceId, placeServiceBinding);
                        return Mono.just(placeServiceBinding);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    public Mono<Boolean> serviceBindingExists(String instanceId, String bindingId) {
        return Mono.just(placeServiceBindings.containsKey(instanceId) &&
                placeServiceBindings.get(instanceId).getBindingId().equalsIgnoreCase(bindingId));
    }

    public Mono<PlaceServiceBinding> getServiceBinding(String instanceId, String bindingId) {
        if (placeServiceBindings.containsKey(instanceId) &&
                placeServiceBindings.get(instanceId).getBindingId().equalsIgnoreCase(bindingId)) {
            return Mono.just(placeServiceBindings.get(instanceId));
        }
        return Mono.empty();
    }

    public Mono<Void> deleteServiceBinding(String instanceId) {
        placeServiceBindings.remove(instanceId);
        return Mono.empty();
    }

    private Map<String, Object> buildCredentials(String instanceId, String bindingId) {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(URI_KEY, instanceId);
        credentials.put(USERNAME_KEY, bindingId);
        credentials.put(PASSWORD_KEY, UUID.randomUUID().toString());
        return credentials;
    }

}
