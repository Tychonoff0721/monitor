package com.example.dsl.functions;

import com.example.dsl.exec.EvalContext;
import com.example.dsl.metadata.EndpointTemplate;
import com.example.dsl.metadata.Instance;
import com.example.dsl.metadata.MetadataRepo;
import com.example.dsl.utils.HttpClient;
import com.example.dsl.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JmxFunction implements DslFunction {
    
    // Simple cache: URL -> {timestamp, data}
    private static final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final long CACHE_TTL_MS = 5000; // 5 seconds cache to merge concurrent requests

    static class CacheEntry {
        long timestamp;
        String data;
        CacheEntry(long t, String d) { timestamp = t; data = d; }
    }

    @Override
    public Object apply(List<Object> args, Map<String, Object> namedArgs, EvalContext ctx) {
        String endpointKey = (String) args.get(0);
        MetadataRepo repo = ctx.getEnv().getRepo();
        
        // 1. Resolve Template
        EndpointTemplate tpl = repo.findEndpointTemplate(endpointKey);
        if (tpl == null) throw new RuntimeException("Unknown endpoint: " + endpointKey);
        
        // 2. Resolve Instances based on Context Scope
        List<Instance> instances = repo.resolveInstances(ctx.getEnv().getTarget(), tpl.getComponent());
        
        List<Map<String, Object>> results = new ArrayList<>();
        
        for (Instance inst : instances) {
            // 3. Construct URL
            String url = constructUrl(inst, tpl);
            
            // 4. Fetch with Cache (Request Merging)
            String json = fetchWithCache(url);
            
            // 5. Parse
            try {
                Map<String, Object> data = JsonUtils.parse(json, Map.class);
                data.put("instance", inst.getInstanceId());
                data.put("host", inst.getHost());
                results.add(data);
            } catch (Exception e) {
                System.err.println("Failed to parse JMX for " + url + ": " + e.getMessage());
            }
        }
        
        return results;
    }
    
    private String constructUrl(Instance inst, EndpointTemplate tpl) {
        // Mock Mode check
        boolean isMock = true; 
        
        if (isMock) {
            // Redirect to local mock controller
            // Pass original query params if needed
            return "http://localhost:8080/mock/jmx?component=" + tpl.getComponent() + "&host=" + inst.getHost();
        } else {
            return tpl.getProtocol() + "://" + inst.getHost() + ":" + tpl.getPort() + tpl.getPrefix();
        }
    }
    
    private String fetchWithCache(String url) {
        long now = System.currentTimeMillis();
        CacheEntry entry = cache.get(url);
        
        if (entry != null && (now - entry.timestamp < CACHE_TTL_MS)) {
            System.out.println("Cache Hit for: " + url);
            return entry.data;
        }
        
        System.out.println("Fetching: " + url);
        try {
            String data = HttpClient.get(url);
            cache.put(url, new CacheEntry(now, data));
            return data;
        } catch (Exception e) {
            return "{}"; // Fail safe
        }
    }
}
