package com.example.dsl.ast;

public class Target {
    private TargetScope scope;
    private String name;

    public Target(TargetScope scope, String name) {
        this.scope = scope;
        this.name = name;
    }

    public TargetScope getScope() { return scope; }
    public void setScope(TargetScope scope) { this.scope = scope; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
