package com.example.dsl.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionCallExpr implements Expr {
    private String functionName;
    private List<Object> args = new ArrayList<>();
    private Map<String, Object> namedArgs; 
    private FunctionCallExpr next; // For the pipeline chain

    public FunctionCallExpr(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() { return functionName; }
    public void setFunctionName(String functionName) { this.functionName = functionName; }

    public List<Object> getArgs() { return args; }
    public void setArgs(List<Object> args) { this.args = args; }

    public Map<String, Object> getNamedArgs() { return namedArgs; }
    public void setNamedArgs(Map<String, Object> namedArgs) { this.namedArgs = namedArgs; }

    public FunctionCallExpr getNext() { return next; }
    public void setNext(FunctionCallExpr next) { this.next = next; }
}
