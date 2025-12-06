package com.example.dsl.exec;

import com.example.dsl.metadata.EndpointTemplate;
import com.example.dsl.metadata.Instance;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResolvedEndpoint {
    private String endpointKey;
    private String baseUrl;
    private Instance instance;
    private EndpointTemplate template;
}
