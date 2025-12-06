package com.example.dsl.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instance {
    private String instanceId;
    private String service;
    private String component;
    private String host;
    private String role; // e.g., ACTIVE, STANDBY
}
