package com.example.dsl.parser;

import com.example.dsl.ast.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class AstBuilder {

    public MetricStmt visitMetricStmt(dslParser.MetricStmtContext ctx) {
        String name = ctx.name.getText();
        Target target = visitTargetScope(ctx.targetScope());
        Expr expr = visitExpr(ctx.expr());
        
        MetricStmt stmt = new MetricStmt();
        stmt.setMetricName(name);
        stmt.setTarget(target);
        stmt.setExpr(expr);
        return stmt;
    }

    public Target visitTargetScope(dslParser.TargetScopeContext ctx) {
        String name = stripQuotes(ctx.name.getText());
        TargetScope scope;
        
        switch (ctx.type.getType()) {
            case dslParser.SERVICE: scope = TargetScope.SERVICE; break;
            case dslParser.COMPONENT: scope = TargetScope.COMPONENT; break;
            case dslParser.INSTANCE: scope = TargetScope.INSTANCE; break;
            case dslParser.CLUSTER: scope = TargetScope.CLUSTER; break;
            default: throw new RuntimeException("Unknown scope type: " + ctx.type.getText());
        }
        
        return new Target(scope, name);
    }

    public Expr visitExpr(dslParser.ExprContext ctx) {
        // expr : functionCallExpr ( '->' functionCallExpr )*
        List<dslParser.FunctionCallExprContext> calls = ctx.functionCallExpr();
        
        FunctionCallExpr head = null;
        FunctionCallExpr current = null;
        
        for (dslParser.FunctionCallExprContext callCtx : calls) {
            FunctionCallExpr expr = visitFunctionCallExpr(callCtx);
            if (head == null) {
                head = expr;
                current = expr;
            } else {
                current.setNext(expr);
                current = expr;
            }
        }
        return head;
    }

    public FunctionCallExpr visitFunctionCallExpr(dslParser.FunctionCallExprContext ctx) {
        String funcName = ctx.functionName.getText();
        FunctionCallExpr expr = new FunctionCallExpr(funcName);
        
        if (ctx.argumentList() != null) {
            visitArgumentList(ctx.argumentList(), expr);
        }
        
        return expr;
    }

    public void visitArgumentList(dslParser.ArgumentListContext ctx, FunctionCallExpr parent) {
        for (dslParser.ArgumentContext argCtx : ctx.argument()) {
            visitArgument(argCtx, parent);
        }
    }

    public void visitArgument(dslParser.ArgumentContext ctx, FunctionCallExpr parent) {
        Object val = visitValue(ctx.value());
        if (ctx.argName != null) {
            String name = ctx.argName.getText();
            if (parent.getNamedArgs() == null) {
                parent.setNamedArgs(new java.util.HashMap<>());
            }
            parent.getNamedArgs().put(name, val);
        } else {
            parent.getArgs().add(val);
        }
    }

    public Object visitValue(dslParser.ValueContext ctx) {
        if (ctx.STRING_LITERAL() != null) {
            return stripQuotes(ctx.STRING_LITERAL().getText());
        } else if (ctx.NUMBER() != null) {
            String text = ctx.NUMBER().getText();
            if (text.contains(".")) return Double.parseDouble(text);
            return Long.parseLong(text);
        } else if (ctx.BOOLEAN() != null) {
            return Boolean.parseBoolean(ctx.BOOLEAN().getText());
        }
        return null;
    }
    
    private String stripQuotes(String s) {
        if (s == null || s.length() < 2) return s;
        char first = s.charAt(0);
        char last = s.charAt(s.length() - 1);
        if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
