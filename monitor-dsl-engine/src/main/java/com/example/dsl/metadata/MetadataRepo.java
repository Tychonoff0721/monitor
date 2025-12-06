package com.example.dsl.metadata;

import java.util.List;

public interface MetadataRepo {
    EndpointTemplate findEndpointTemplate(String endpointKey);
    Instance findOneInstance(String service, String component, String role);
    List<Instance> findInstances(String service, String component);
}
