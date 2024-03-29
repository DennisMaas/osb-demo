package de.dennismaas.osbdemo.servicebroker.config;

import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CatalogConfig {

    @Bean
    public Catalog catalog(){
        Plan plan = Plan.builder()
                .id("uniquePlanId")
                .name("simple-plan-name")
                .description("simple plan")
                .free(true)
                .build();

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
                .id("uniqueServiceDefinitionId")
                .name("simple-service-definition-name")
                .description("simple service definition")
                .bindable(true)
                .tags("example", "tags")
                .plans(plan)
                .build();

        return Catalog.builder()
                .serviceDefinitions(serviceDefinition)
                .build();
    }
}
