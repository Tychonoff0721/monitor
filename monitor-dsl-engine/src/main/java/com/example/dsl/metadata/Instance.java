package com.example.dsl.metadata;

public class Instance {
    private String instanceId;
    private String clusterName; // Added cluster
    private String serviceName; // Renamed from service for clarity (e.g., nameservice1)
    private String component;   // e.g., NameNode, Router, ResourceManager
    private String host;
    private int port;           // Added port
    private String role;        // e.g., ACTIVE, STANDBY

    public Instance() {}

    public Instance(String instanceId, String clusterName, String serviceName, String component, String host, int port, String role) {
        this.instanceId = instanceId;
        this.clusterName = clusterName;
        this.serviceName = serviceName;
        this.component = component;
        this.host = host;
        this.port = port;
        this.role = role;
    }

    public String getInstanceId() { return instanceId; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }

    public String getClusterName() { return clusterName; }
    public void setClusterName(String clusterName) { this.clusterName = clusterName; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
