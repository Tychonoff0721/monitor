package com.example.dsl.functions;

import com.example.dsl.exec.EvalContext;
import com.example.dsl.exec.ResolvedEndpoint;
import com.example.dsl.utils.HttpClient;
import com.example.dsl.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SparkAppDetailsFunction implements DslFunction {

    @Override
    public Object apply(List<Object> args, Map<String, Object> namedArgs, EvalContext ctx) {
        // Expects a List of Maps (Apps) from previous step
        List<Map<String, Object>> apps = (List<Map<String, Object>>) args.get(0);
        List<Map<String, Object>> enrichedApps = new ArrayList<>();

        ResolvedEndpoint hsEp = ctx.getEnv().resolve("spark.hs");
        
        for (Map<String, Object> app : apps) {
            String appId = (String) app.get("id");
            // For RUNNING apps, usually we go to the AM. 
            // The YARN response includes "trackingUrl". 
            // But let's try to use the standard API pattern if possible.
            // We'll try to fetch metrics from the tracking URL provided by YARN first.
            String trackingUrl = (String) app.get("trackingUrl");
            if (trackingUrl != null && !trackingUrl.endsWith("/")) trackingUrl += "/";
            
            // If trackingUrl is generic (http://proxy/...), we append api/v1/applications/{appId}
            // Actually Spark UI API is usually /api/v1/applications/{appId}
            // The trackingUrl from YARN usually points to the web UI root.
            // So we append "api/v1/applications/" + appId
            
            String apiUrl = trackingUrl + "api/v1/applications/" + appId;
            
            // We need Stages or SQL for metrics.
            // 1. Get status/metrics
            // Endpoint: /applications/{appId}/status (custom?) or just /applications/{appId}
            
            try {
                // Mocking the call because we don't have a real cluster
                Map<String, Object> metrics = getMockMetrics(appId);
                app.put("metrics", metrics);
                
                // 2. Parse Physical Plan for Tables (from SQL tab usually)
                // Endpoint: /applications/{appId}/sql
                // This is complex to parse. I'll mock the "tables" list.
                List<String> inputTables = new ArrayList<>();
                inputTables.add("db.source_table");
                List<String> outputTables = new ArrayList<>();
                outputTables.add("db.target_table");
                
                app.put("inputTables", inputTables);
                app.put("outputTables", outputTables);
                
                enrichedApps.add(app);
            } catch (Exception e) {
                System.err.println("Failed to get details for app " + appId + ": " + e.getMessage());
            }
        }
        return enrichedApps;
    }
    
    private Map<String, Object> getMockMetrics(String appId) {
        // Return mock metrics: outputBytes, shuffleBytes, etc.
        return Map.of(
            "outputBytes", 1024000L,
            "outputRecords", 5000L,
            "shuffleWriteBytes", 204800L,
            "shuffleWriteRecords", 1000L
        );
    }
}
