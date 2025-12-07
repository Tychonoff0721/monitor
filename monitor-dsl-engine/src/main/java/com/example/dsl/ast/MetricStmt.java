package com.example.dsl.ast;

public class MetricStmt {
    private String metricName;
    private Target target;
    private Expr expr; // The head of the pipeline

    public String getMetricName() { return metricName; }
    public void setMetricName(String metricName) { this.metricName = metricName; }

    public Target getTarget() { return target; }
    public void setTarget(Target target) { this.target = target; }

    public Expr getExpr() { return expr; }
    public void setExpr(Expr expr) { this.expr = expr; }
}
