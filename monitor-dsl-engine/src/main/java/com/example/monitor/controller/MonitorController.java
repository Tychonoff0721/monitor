package com.example.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MonitorController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/jobs")
    public List<Map<String, Object>> getJobs() {
        return jdbcTemplate.queryForList("SELECT * FROM job_metrics ORDER BY submit_time DESC LIMIT 100");
    }
    
    @GetMapping("/slow-jobs")
    public List<Map<String, Object>> getSlowJobs() {
        // Assuming duration > 30 min is slow, or has a slow_reason
        return jdbcTemplate.queryForList("SELECT * FROM job_metrics WHERE slow_reason IS NOT NULL OR duration > 1800000 ORDER BY duration DESC LIMIT 100");
    }

    @GetMapping("/job/{appId}/details")
    public Map<String, Object> getJobDetails(@org.springframework.web.bind.annotation.PathVariable String appId) {
        Map<String, Object> result = new java.util.HashMap<>();
        
        // Basic Info
        try {
            Map<String, Object> job = jdbcTemplate.queryForMap("SELECT * FROM job_metrics WHERE app_id = ?", appId);
            result.put("job", job);
        } catch (Exception e) {
            result.put("error", "Job not found");
            return result;
        }
        
        // Stages
        List<Map<String, Object>> stages = jdbcTemplate.queryForList("SELECT * FROM stage_metrics WHERE app_id = ? ORDER BY stage_id", appId);
        result.put("stages", stages);
        
        // Tables
        List<Map<String, Object>> tables = jdbcTemplate.queryForList("SELECT * FROM table_metrics WHERE app_id = ?", appId);
        result.put("tables", tables);
        
        return result;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Integer totalJobs = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM job_metrics", Integer.class);
        Long totalOutput = jdbcTemplate.queryForObject("SELECT SUM(output_bytes) FROM job_metrics", Long.class);
        Long totalShuffle = jdbcTemplate.queryForObject("SELECT SUM(shuffle_bytes) FROM job_metrics", Long.class);
        
        return Map.of(
            "totalJobs", totalJobs != null ? totalJobs : 0,
            "totalOutputBytes", totalOutput != null ? totalOutput : 0L,
            "totalShuffleBytes", totalShuffle != null ? totalShuffle : 0L
        );
    }
    
    @GetMapping("/tables")
    public List<Map<String, Object>> getTopTables() {
        return jdbcTemplate.queryForList("SELECT table_name, SUM(space_bytes) as total_space FROM table_metrics GROUP BY table_name ORDER BY total_space DESC LIMIT 10");
    }
}
