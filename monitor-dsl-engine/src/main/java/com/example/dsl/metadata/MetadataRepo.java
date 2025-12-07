package com.example.dsl.metadata;

import java.util.List;
import com.example.dsl.ast.TargetScope;

public interface MetadataRepo {
    EndpointTemplate findEndpointTemplate(String endpointKey);
    Instance findOneInstance(String service, String component, String role);
    List<Instance> findInstances(String service, String component);
    
    // New method
    List<Instance> resolveInstances(com.example.dsl.ast.Target target, String requiredComponent);
}
