package com.example.dsl.functions;

import com.example.dsl.exec.EvalContext;
import com.example.dsl.utils.HttpClient;
import com.example.dsl.utils.JsonUtils;

import java.util.List;
import java.util.Map;

public class HdfsTableStatsFunction implements DslFunction {

    @Override
    public Object apply(List<Object> args, Map<String, Object> namedArgs, EvalContext ctx) {
        List<Map<String, Object>> tables = (List<Map<String, Object>>) args.get(0);
        
        // In real life, we would resolve HMS endpoint from ctx.getEnv().resolve("hive.metastore")
        // But here we use the Mock Service we just built.
        
        for (Map<String, Object> table : tables) {
            String tableName = (String) table.get("tableName");
            if (tableName == null) continue;

            String[] parts = tableName.split("\\.");
            String db = parts.length > 1 ? parts[0] : "default";
            String tbl = parts.length > 1 ? parts[1] : parts[0];
            
            // Call Mock HMS Service
            // API: /mock/hms/table/{db}/{table}
            try {
                 String url = "http://localhost:8080/mock/hms/table/" + db + "/" + tbl;
                 String json = HttpClient.get(url);
                 Map<String, Object> stats = JsonUtils.parse(json, Map.class);
                 
                 if (stats != null) {
                     table.put("spaceBytes", stats.get("totalSize"));
                     table.put("fileCount", stats.get("numFiles"));
                     table.put("format", stats.get("format"));
                 }
            } catch (Exception e) {
                System.err.println("Failed to fetch stats for " + tableName + ": " + e.getMessage());
                table.put("error", "HMS Fetch Failed");
            }
        }
        
        return tables;
    }
}
