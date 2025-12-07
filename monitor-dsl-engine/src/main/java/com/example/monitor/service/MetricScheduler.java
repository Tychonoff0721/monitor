package com.example.monitor.service;

import com.example.monitor.model.MetricConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MetricScheduler {

    @Autowired
    private DslService dslService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // We still keep results in memory for now, as storing complex JSON results in DB is tricky without JSON type
    private final Map<String, Object> latestResults = new ConcurrentHashMap<>();
    
    private final RowMapper<MetricConfig> configMapper = (rs, rowNum) -> new MetricConfig(
            rs.getString("id"),
            rs.getString("description"),
            rs.getString("dsl"),
            rs.getLong("frequency_ms"),
            rs.getString("analysis_type"),
            rs.getLong("last_run_time")
    );

    public List<MetricConfig> getAllConfigs() {
        return jdbcTemplate.query("SELECT * FROM metric_configs", configMapper);
    }

    public void addConfig(MetricConfig config) {
        jdbcTemplate.update("INSERT INTO metric_configs VALUES (?, ?, ?, ?, ?, ?)",
                config.getId(),
                config.getDescription(),
                config.getDsl(),
                config.getFrequencyMs(),
                config.getAnalysisType(),
                0L);
    }
    
    public void deleteConfig(String id) {
        jdbcTemplate.update("DELETE FROM metric_configs WHERE id = ?", id);
    }
    
    public Object getResult(String id) {
        return latestResults.get(id);
    }

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000) // Check every 5 seconds
    public void schedule() {
        long now = System.currentTimeMillis();
        
        List<MetricConfig> configs = getAllConfigs();
        
        for (MetricConfig config : configs) {
            if (now - config.getLastRunTime() >= config.getFrequencyMs()) {
                System.out.println("Executing Metric: " + config.getDescription());
                try {
                    Object result = dslService.execute(config.getDsl());
                    latestResults.put(config.getId(), result);
                    
                    // Save to History
                    String jsonResult = objectMapper.writeValueAsString(result);
                    jdbcTemplate.update("INSERT INTO metric_history (config_id, timestamp, result_json) VALUES (?, ?, ?)",
                            config.getId(), now, jsonResult);
                    
                    // Update Last Run Time
                    jdbcTemplate.update("UPDATE metric_configs SET last_run_time = ? WHERE id = ?", now, config.getId());
                    
                } catch (Exception e) {
                    System.err.println("Failed to execute metric " + config.getId() + ": " + e.getMessage());
                }
            }
        }
    }
}
