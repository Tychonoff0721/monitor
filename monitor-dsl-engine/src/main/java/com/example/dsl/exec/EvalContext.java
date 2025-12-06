package com.example.dsl.exec;

import java.util.HashMap;
import java.util.Map;

public class EvalContext {
    private final ExecutionEnv env;
    private final FunctionRegistry registry;
    private final Map<String, Object> variables = new HashMap<>();

    public EvalContext(ExecutionEnv env, FunctionRegistry registry) {
        this.env = env;
        this.registry = registry;
    }

    public ExecutionEnv getEnv() {
        return env;
    }

    public FunctionRegistry getRegistry() {
        return registry;
    }

    public void setVar(String name, Object value) {
        variables.put(name, value);
    }

    public Object getVar(String name) {
        return variables.get(name);
    }
}
