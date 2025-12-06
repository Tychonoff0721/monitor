package com.example.dsl.functions;

import com.example.dsl.exec.EvalContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractTableSpacesFunction implements DslFunction {

    @Override
    public Object apply(List<Object> args, Map<String, Object> namedArgs, EvalContext ctx) {
        // Input: List of Apps (Maps)
        List<Map<String, Object>> apps = (List<Map<String, Object>>) args.get(0);
        List<Map<String, Object>> tables = new ArrayList<>();
        
        for (Map<String, Object> app : apps) {
            String appId = (String) app.get("id");
            List<String> inputs = (List<String>) app.get("inputTables");
            List<String> outputs = (List<String>) app.get("outputTables");
            
            if (inputs != null) {
                for (String t : inputs) {
                    tables.add(createTableObj(appId, t, "INPUT"));
                }
            }
            if (outputs != null) {
                for (String t : outputs) {
                    tables.add(createTableObj(appId, t, "OUTPUT"));
                }
            }
        }
        return tables;
    }
    
    private Map<String, Object> createTableObj(String appId, String tableName, String type) {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("appId", appId);
        map.put("tableName", tableName);
        map.put("type", type);
        return map;
    }
}
