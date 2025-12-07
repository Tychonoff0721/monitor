package com.example.dsl.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.dsl.ast.TargetScope;

public class MemoryMetadataRepo implements MetadataRepo {
    private Map<String, EndpointTemplate> templates = new HashMap<>();
    private List<Instance> instances = new ArrayList<>();

    public MemoryMetadataRepo() {
        // Init default templates
        addTemplate(new EndpointTemplate("hdfs.nn", "NameNode", "http", 9870, "/jmx"));
        addTemplate(new EndpointTemplate("hdfs.router", "Router", "http", 50071, "/jmx"));
        addTemplate(new EndpointTemplate("yarn.rm", "ResourceManager", "http", 8088, "/jmx"));
        
        // Init default instances (Mock Topology)
        // Cluster: mrs1
        // Service: nameservice1
        addInstance(new Instance("nn1", "mrs1", "nameservice1", "NameNode", "localhost", 9870, "ACTIVE"));
        addInstance(new Instance("nn2", "mrs1", "nameservice1", "NameNode", "localhost", 9871, "STANDBY"));
        
        // Router (Service: router-service)
        addInstance(new Instance("r1", "mrs1", "router-service", "Router", "localhost", 50071, "ACTIVE"));
        
        // YARN
        addInstance(new Instance("rm1", "mrs1", "yarn-service", "ResourceManager", "localhost", 8088, "ACTIVE"));
    }

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
        // Legacy method support
        return instances.stream()
                .filter(i -> (service == null || i.getServiceName().equals(service))
                        && i.getComponent().equals(component)
                        && (role == null || i.getRole().equals(role)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Instance> findInstances(String service, String component) {
        return instances.stream()
                .filter(i -> (service == null || i.getServiceName().equals(service))
                        && i.getComponent().equals(component))
                .collect(Collectors.toList());
    }
    
    // New method for flexible scoping
    @Override
    public List<Instance> resolveInstances(com.example.dsl.ast.Target target, String requiredComponent) {
        return instances.stream()
            .filter(i -> {
                boolean matchComponent = i.getComponent().equalsIgnoreCase(requiredComponent);
                if (!matchComponent) return false;
                
                TargetScope scope = target.getScope();
                String targetName = target.getName().replace("\"", "");

                if (scope == TargetScope.CLUSTER) {
                    return i.getClusterName().equals(targetName);
                } else if (scope == TargetScope.SERVICE) {
                    // Assuming scope name is like "mrs1/nameservice1" or just "nameservice1"
                    if (targetName.contains("/")) {
                        String[] parts = targetName.split("/");
                        return i.getClusterName().equals(parts[0]) && i.getServiceName().equals(parts[1]);
                    } else {
                        return i.getServiceName().equals(targetName);
                    }
                }
                return false;
            })
            .collect(Collectors.toList());
    }
}
