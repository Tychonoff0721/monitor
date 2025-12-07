package com.example.dsl.exec;

import com.example.dsl.metadata.EndpointTemplate;
import com.example.dsl.metadata.Instance;

public class ResolvedEndpoint {
    private String endpointKey;
    private String baseUrl;
    private Instance instance;
    private EndpointTemplate template;

    public ResolvedEndpoint(String endpointKey, String baseUrl, Instance instance, EndpointTemplate template) {
        this.endpointKey = endpointKey;
        this.baseUrl = baseUrl;
        this.instance = instance;
        this.template = template;
    }

    public String getEndpointKey() { return endpointKey; }
    public void setEndpointKey(String endpointKey) { this.endpointKey = endpointKey; }

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public Instance getInstance() { return instance; }
    public void setInstance(Instance instance) { this.instance = instance; }

    public EndpointTemplate getTemplate() { return template; }
    public void setTemplate(EndpointTemplate template) { this.template = template; }
}
