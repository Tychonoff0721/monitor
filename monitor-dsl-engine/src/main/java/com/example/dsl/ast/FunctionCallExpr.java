package com.example.dsl.ast;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class FunctionCallExpr implements Expr {
    private String functionName;
    private List<Object> args = new ArrayList<>();
    private Map<String, Object> namedArgs; 
    private FunctionCallExpr next; // For the pipeline chain

    public FunctionCallExpr(String functionName) {
        this.functionName = functionName;
    }
}
