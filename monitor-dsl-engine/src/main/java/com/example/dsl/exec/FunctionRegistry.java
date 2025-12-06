package com.example.dsl.exec;

import com.example.dsl.functions.DslFunction;
import java.util.HashMap;
import java.util.Map;

public class FunctionRegistry {
    private final Map<String, DslFunction> functions = new HashMap<>();

    public void register(String name, DslFunction func) {
        functions.put(name, func);
    }

    public DslFunction lookup(String name) {
        return functions.get(name);
    }
}
