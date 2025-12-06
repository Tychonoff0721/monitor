package com.example.dsl.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryMetadataRepo implements MetadataRepo {
    private Map<String, EndpointTemplate> templates = new HashMap<>();
    private List<Instance> instances = new ArrayList<>();

    public void addTemplate(EndpointTemplate t) {
        templates.put(t.getEndpointKey(), t);
    }

    public void addInstance(Instance i) {
        instances.add(i);
    }

    @Override
    public EndpointTemplate findEndpointTemplate(String endpointKey) {
        return templates.get(endpointKey);
    }

    @Override
    public Instance findOneInstance(String service, String component, String role) {
        return instances.stream()
                .filter(i -> i.getService().equals(service) 
                        && i.getComponent().equals(component) 
                        && (role == null || i.getRole().equals(role)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Instance> findInstances(String service, String component) {
         return instances.stream()
                .filter(i -> i.getService().equals(service) 
                        && i.getComponent().equals(component))
                .collect(Collectors.toList());
    }
}
