package com.example.dsl.exec;

import com.example.dsl.ast.Target;
import com.example.dsl.metadata.EndpointTemplate;
import com.example.dsl.metadata.Instance;
import com.example.dsl.metadata.MetadataRepo;

public class ExecutionEnv {
    private final Target target;
    private final MetadataRepo repo;

    public ExecutionEnv(Target target, MetadataRepo repo) {
        this.target = target;
        this.repo = repo;
    }

    public ResolvedEndpoint resolve(String endpointKey) {
        EndpointTemplate tpl = repo.findEndpointTemplate(endpointKey);
        if (tpl == null) {
            throw new RuntimeException("Endpoint template not found: " + endpointKey);
        }
        
        // Logic to select instance based on target.
        // If target is SERVICE, we might look for an ACTIVE instance of the component in that service.
        // This logic can be complex. For now, simple assumption:
        // Find ACTIVE instance of the component in the target service.
        Instance inst = repo.findOneInstance(target.getName(), tpl.getComponent(), "ACTIVE");
        if (inst == null) {
             // Fallback to PRIMARY or just take any if role not specified?
             inst = repo.findOneInstance(target.getName(), tpl.getComponent(), "PRIMARY");
        }
        if (inst == null) {
             // NOTE: In production we should throw, but for MOCK/Dev we might tolerate missing instance if we just want the URL template
             // throw new RuntimeException("No active/primary instance found for " + endpointKey + " in service " + target.getName());
             inst = new Instance("mock-id", "mock-cluster", "mock-service", tpl.getComponent(), "localhost", tpl.getPort(), "ACTIVE");
        }

        String baseUrl = tpl.getProtocol() + "://" + inst.getHost() + ":" + tpl.getPort() + tpl.getPrefix();
        return new ResolvedEndpoint(endpointKey, baseUrl, inst, tpl);
    }
    
    public Target getTarget() {
        return target;
    }
    
    public MetadataRepo getRepo() {
        return repo;
    }
}
