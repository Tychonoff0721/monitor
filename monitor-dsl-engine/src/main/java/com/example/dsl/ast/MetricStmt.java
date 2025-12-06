package com.example.dsl.ast;

import lombok.Data;

@Data
public class MetricStmt {
    private String metricName;
    private Target target;
    private Expr expr; // The head of the pipeline
}
