package com.example.dsl.exec;

import com.example.dsl.ast.FunctionCallExpr;
import com.example.dsl.functions.DslFunction;

import java.util.Collections;
import java.util.List;

public class ExprEvaluator {
    
    public Object evaluate(com.example.dsl.ast.Expr expr, EvalContext ctx) {
        if (expr instanceof FunctionCallExpr) {
            return evaluate((FunctionCallExpr) expr, ctx);
        }
        throw new IllegalArgumentException("Unsupported expression type: " + expr.getClass().getName());
    }

    public Object evaluate(FunctionCallExpr expr, EvalContext ctx) {
        Object currentValue = null;
        FunctionCallExpr currentExpr = expr;
        
        while (currentExpr != null) {
            DslFunction func = ctx.getRegistry().lookup(currentExpr.getFunctionName());
            if (func == null) {
                throw new RuntimeException("Function not found: " + currentExpr.getFunctionName());
            }
            
            // If this is not the first function in chain, the result of previous is passed as first arg?
            // Or as a specific context var?
            // The example DSL: yarn_running_apps() -> spark_app_details()
            // spark_app_details needs the output of yarn_running_apps.
            // I'll assume the result of the previous function is prepended to the args list 
            // OR available in context. Prepending to args is standard pipeline behavior.
            
            List<Object> args = new java.util.ArrayList<>(currentExpr.getArgs());
            if (currentValue != null) {
                // If previous result is a List, do we flat map? 
                // Or just pass the list? 
                // yarn_running_apps returns a List of Apps.
                // spark_app_details() likely iterates over them.
                // But the pipeline usually implies: 
                // func1 -> func2
                // func2(func1_result, other_args)
                args.add(0, currentValue);
            }
            
            currentValue = func.apply(args, currentExpr.getNamedArgs(), ctx);
            currentExpr = currentExpr.getNext();
        }
        
        return currentValue;
    }
}
