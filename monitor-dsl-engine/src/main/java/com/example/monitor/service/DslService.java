package com.example.monitor.service;

import com.example.dsl.ast.MetricStmt;
import com.example.dsl.exec.EvalContext;
import com.example.dsl.exec.ExecutionEnv;
import com.example.dsl.exec.ExprEvaluator;
import com.example.dsl.exec.FunctionRegistry;
import com.example.dsl.functions.*;
import com.example.dsl.metadata.MetadataRepo;
import com.example.dsl.parser.MonitorDslParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DslService {
    private final MonitorDslParser parser = new MonitorDslParser();
    
    @Autowired
    private MetadataRepo repo;
    
    private final FunctionRegistry registry = new FunctionRegistry();

    @PostConstruct
    public void init() {
        registry.register("yarn_running_apps", new YarnRunningAppsFunction());
        registry.register("extract_tables", new ExtractTableSpacesFunction());
        registry.register("hdfs_table_stats", new HdfsTableStatsFunction());
        registry.register("spark_details", new SparkAppDetailsFunction());
        registry.register("sum", new SumFieldFunction());
        registry.register("jmx_func", new JmxFunction());
    }

    public Object execute(String code) {
        try {
            MetricStmt stmt = parser.parse(code);
            ExecutionEnv env = new ExecutionEnv(stmt.getTarget(), repo);
            EvalContext ctx = new EvalContext(env, registry);
            return new ExprEvaluator().evaluate(stmt.getExpr(), ctx);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
