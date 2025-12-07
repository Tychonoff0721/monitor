package com.example.dsl.metadata;

public class EndpointTemplate {
    private String endpointKey;
    private String component;
    private String protocol;
    private int port;
    private String prefix;

    public EndpointTemplate() {}

    public EndpointTemplate(String endpointKey, String component, String protocol, int port, String prefix) {
        this.endpointKey = endpointKey;
        this.component = component;
        this.protocol = protocol;
        this.port = port;
        this.prefix = prefix;
    }

    public String getEndpointKey() { return endpointKey; }
    public void setEndpointKey(String endpointKey) { this.endpointKey = endpointKey; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public String getProtocol() { return protocol; }
    public void setProtocol(String protocol) { this.protocol = protocol; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
}
