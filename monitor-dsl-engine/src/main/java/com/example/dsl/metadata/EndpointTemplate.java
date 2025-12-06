package com.example.dsl.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointTemplate {
    private String endpointKey;
    private String component;
    private String protocol;
    private int port;
    private String prefix;
}
