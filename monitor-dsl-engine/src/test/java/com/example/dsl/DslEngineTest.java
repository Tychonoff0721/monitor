package com.example.dsl;

import com.example.dsl.ast.MetricStmt;
import com.example.dsl.exec.EvalContext;
import com.example.dsl.exec.ExecutionEnv;
import com.example.dsl.exec.ExprEvaluator;
import com.example.dsl.exec.FunctionRegistry;
import com.example.dsl.functions.*;
import com.example.dsl.metadata.EndpointTemplate;
import com.example.dsl.metadata.Instance;
import com.example.dsl.metadata.MemoryMetadataRepo;
import com.example.dsl.parser.MonitorDslParser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DslEngineTest {

    @Test
    public void testDslPipeline() {
        System.setProperty("monitor.mock", "true");
        // 1. Prepare Metadata
        MemoryMetadataRepo repo = new MemoryMetadataRepo();
        repo.addTemplate(new EndpointTemplate("yarn.rm", "yarn-rm", "http", 8088, "/ws/v1/cluster"));
        repo.addTemplate(new EndpointTemplate("spark.hs", "spark-hs", "http", 18080, "/api/v1"));
        repo.addTemplate(new EndpointTemplate("hdfs.router", "hdfs-router", "http", 9870, "/webhdfs/v1"));
        repo.addInstance(new Instance("prod1-rm1", "bdp_prod", "yarn-rm", "rm1.prod.com", "ACTIVE"));
        repo.addInstance(new Instance("prod1-hs1", "bdp_prod", "spark-hs", "hs1.prod.com", "PRIMARY"));
        repo.addInstance(new Instance("prod1-rt1", "bdp_prod", "hdfs-router", "rt1.prod.com", "PRIMARY"));

        // 2. Register Functions
        FunctionRegistry registry = new FunctionRegistry();
        registry.register("yarn_running_apps", new YarnRunningAppsFunction());
        registry.register("spark_app_details", new SparkAppDetailsFunction());
        registry.register("extract_table_spaces", new ExtractTableSpacesFunction());
        registry.register("hdfs_table_stats", new HdfsTableStatsFunction());
        registry.register("sum_field", new SumFieldFunction());

        // 3. Parse DSL
        String dsl = "METRIC spark_table_stats " +
                     "ON SERVICE 'bdp_prod' " +
                     "AS yarn_running_apps(queue='etl') " +
                     "   -> spark_app_details() " +
                     "   -> extract_table_spaces() " +
                     "   -> hdfs_table_stats();";
                     
        MonitorDslParser parser = new MonitorDslParser();
        MetricStmt stmt = parser.parse(dsl);
        
        assertNotNull(stmt);
        assertEquals("spark_table_stats", stmt.getMetricName());
        assertEquals("bdp_prod", stmt.getTarget().getName());

        // 4. Execute
        ExecutionEnv env = new ExecutionEnv(stmt.getTarget(), repo);
        EvalContext ctx = new EvalContext(env, registry);
        ExprEvaluator evaluator = new ExprEvaluator();
        
        Object result = evaluator.evaluate(stmt.getExpr(), ctx);
        
        // 5. Verify Result
        assertNotNull(result);
        List<Map<String, Object>> tables = (List<Map<String, Object>>) result;
        System.out.println("Resulting Tables: " + tables);
        
        // Check if we got data (based on mocks)
        // Yarn mock returns 1 app.
        // Spark mock adds input/output tables (2 tables).
        // HDFS mock adds stats.
        // So we expect 2 tables in the list.
        assertEquals(2, tables.size());
    }
}
