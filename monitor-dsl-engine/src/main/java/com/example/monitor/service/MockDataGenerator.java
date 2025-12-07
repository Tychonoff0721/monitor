package com.example.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Service
public class MockDataGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initData() {
        try {
            // Create Tables if not exist (Spring Boot might do this if we used Entity, but we use JDBC)
            createTables();
            
            // Check if data exists
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM job_metrics", Integer.class);
            if (count != null && count == 0) {
                System.out.println("Generating Mock Data...");
                generateMockData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS stage_metrics");
        jdbcTemplate.execute("DROP TABLE IF EXISTS job_metrics");
        jdbcTemplate.execute("DROP TABLE IF EXISTS table_metrics");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS job_metrics (" +
                "app_id VARCHAR(64) PRIMARY KEY, " +
                "name VARCHAR(128), " +
                "submit_time TIMESTAMP, " +
                "duration BIGINT, " +
                "output_bytes BIGINT, " +
                "output_records BIGINT, " +
                "shuffle_bytes BIGINT, " +
                "shuffle_records BIGINT, " +
                "cpu_vcore_seconds BIGINT, " +
                "memory_mb_seconds BIGINT, " +
                "slow_reason VARCHAR(64), " +
                "updated_at TIMESTAMP)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS table_metrics (" +
                "app_id VARCHAR(64), " +
                "table_name VARCHAR(128), " +
                "type VARCHAR(32), " +
                "space_bytes BIGINT, " +
                "file_count BIGINT, " +
                "partition_count INT, " +
                "format VARCHAR(32), " +
                "compression VARCHAR(32), " +
                "updated_at TIMESTAMP, " +
                "PRIMARY KEY(app_id, table_name))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS stage_metrics (" +
                "stage_id VARCHAR(64), " +
                "app_id VARCHAR(64), " +
                "name VARCHAR(64), " +
                "status VARCHAR(32), " +
                "duration BIGINT, " +
                "tasks INT, " +
                "input_bytes BIGINT, " +
                "output_bytes BIGINT, " +
                "shuffle_read_bytes BIGINT, " +
                "shuffle_write_bytes BIGINT, " +
                "skew_score DOUBLE, " +
                "updated_at TIMESTAMP, " +
                "PRIMARY KEY(stage_id, app_id))");
        
        // New Metadata Tables
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS endpoint_templates (" +
                "endpoint_key VARCHAR(64) PRIMARY KEY, " +
                "component VARCHAR(64), " +
                "protocol VARCHAR(16), " +
                "port INT, " +
                "prefix VARCHAR(128))");
                
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS instances (" +
                "instance_id VARCHAR(64) PRIMARY KEY, " +
                "cluster_name VARCHAR(64), " +
                "service_name VARCHAR(64), " +
                "component VARCHAR(64), " +
                "host VARCHAR(128), " +
                "port INT, " +
                "role VARCHAR(32))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS metric_configs (" +
                "id VARCHAR(64) PRIMARY KEY, " +
                "description VARCHAR(255), " +
                "dsl VARCHAR(1024), " +
                "frequency_ms BIGINT, " +
                "analysis_type VARCHAR(32), " +
                "last_run_time BIGINT)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS metric_history (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "config_id VARCHAR(64), " +
                "timestamp BIGINT, " +
                "result_json TEXT)");
    }

    private void generateMockData() {
        // Clear old history on init if needed, or just keep appending
        // jdbcTemplate.execute("DELETE FROM metric_history");
        // Insert Default Metadata
        jdbcTemplate.execute("DELETE FROM endpoint_templates");
        jdbcTemplate.update("INSERT INTO endpoint_templates VALUES ('hdfs.nn', 'NameNode', 'http', 9870, '/jmx')");
        jdbcTemplate.update("INSERT INTO endpoint_templates VALUES ('hdfs.router', 'Router', 'http', 50071, '/jmx')");
        jdbcTemplate.update("INSERT INTO endpoint_templates VALUES ('yarn.rm', 'ResourceManager', 'http', 8088, '/jmx')");
        
        jdbcTemplate.execute("DELETE FROM instances");
        jdbcTemplate.update("INSERT INTO instances VALUES ('nn1', 'mrs1', 'nameservice1', 'NameNode', 'localhost', 9870, 'ACTIVE')");
        jdbcTemplate.update("INSERT INTO instances VALUES ('nn2', 'mrs1', 'nameservice1', 'NameNode', 'localhost', 9871, 'STANDBY')");
        jdbcTemplate.update("INSERT INTO instances VALUES ('r1', 'mrs1', 'router-service', 'Router', 'localhost', 50071, 'ACTIVE')");
        jdbcTemplate.update("INSERT INTO instances VALUES ('rm1', 'mrs1', 'yarn-service', 'ResourceManager', 'localhost', 8088, 'ACTIVE')");
        
        jdbcTemplate.execute("DELETE FROM metric_configs");
        jdbcTemplate.update("INSERT INTO metric_configs VALUES ('1', 'Check NameNode RPC', 'METRIC rpc_queue ON CLUSTER \"mrs1\" AS jmx_func(\"hdfs.nn\")', 30000, 'map', 0)");
        jdbcTemplate.update("INSERT INTO metric_configs VALUES ('2', 'Check Router RPC', 'METRIC router_rpc ON CLUSTER \"mrs1\" AS jmx_func(\"hdfs.router\")', 30000, 'map', 0)");

        Random random = new Random();
        List<Object[]> jobArgs = new ArrayList<>();
        List<Object[]> tableArgs = new ArrayList<>();
        List<Object[]> stageArgs = new ArrayList<>();
        long now = System.currentTimeMillis();
        String[] slowReasons = {"Data Skew", "Resource Starvation", "GC Overhead", "Large Shuffle", "Complex DAG", null, null, null};
        String[] formats = {"Parquet", "ORC", "Avro", "Text"};
        String[] compressions = {"Snappy", "Zlib", "LZO", "None"};

        for (int i = 0; i < 150; i++) {
            String appId = "application_" + (10000 + i);
            long duration = 1800000 + random.nextInt(3600000); // 30m - 90m
            long submitTime = now - duration - random.nextInt(3600000);
            String slowReason = slowReasons[random.nextInt(slowReasons.length)];
            
            jobArgs.add(new Object[]{
                    appId,
                    "Spark Job " + (i + 1),
                    new Timestamp(submitTime),
                    duration,
                    random.nextLong() & Long.MAX_VALUE % 10000000000L, // Output bytes
                    random.nextLong() & Long.MAX_VALUE % 1000000L, // Output records
                    random.nextLong() & Long.MAX_VALUE % 5000000000L, // Shuffle bytes
                    random.nextLong() & Long.MAX_VALUE % 500000L, // Shuffle records
                    random.nextLong() & Long.MAX_VALUE % 100000L, // CPU vcore seconds
                    random.nextLong() & Long.MAX_VALUE % 1000000L, // Memory MB seconds
                    slowReason,
                    new Timestamp(now)
            });

            // Generate tables for this job
            int tableCount = 1 + random.nextInt(3);
            Set<String> usedTables = new HashSet<>();
            for (int j = 0; j < tableCount; j++) {
                String tableName;
                do {
                    tableName = "db_" + (random.nextInt(5) + 1) + ".table_" + (random.nextInt(20) + 1);
                } while (usedTables.contains(tableName));
                usedTables.add(tableName);

                tableArgs.add(new Object[]{
                        appId,
                        tableName,
                        random.nextBoolean() ? "INPUT" : "OUTPUT",
                        random.nextLong() & Long.MAX_VALUE % 100000000000L, // 100GB max
                        random.nextInt(1000), // File count
                        random.nextInt(50), // Partition count
                        formats[random.nextInt(formats.length)],
                        compressions[random.nextInt(compressions.length)],
                        new Timestamp(now)
                });
            }

            // Generate stages for this job
            int stageCount = 3 + random.nextInt(5);
            for (int k = 0; k < stageCount; k++) {
                stageArgs.add(new Object[]{
                        "stage_" + k,
                        appId,
                        "Stage " + k,
                        "SUCCEEDED",
                        duration / stageCount + random.nextInt(10000),
                        10 + random.nextInt(100), // Tasks
                        random.nextLong() & Long.MAX_VALUE % 1000000000L, // Input
                        random.nextLong() & Long.MAX_VALUE % 1000000000L, // Output
                        random.nextLong() & Long.MAX_VALUE % 1000000000L, // Shuffle Read
                        random.nextLong() & Long.MAX_VALUE % 1000000000L, // Shuffle Write
                        random.nextDouble(), // Skew score 0.0 - 1.0
                        new Timestamp(now)
                });
            }
        }

        jdbcTemplate.batchUpdate("INSERT INTO job_metrics VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", jobArgs);
        jdbcTemplate.batchUpdate("INSERT INTO table_metrics VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", tableArgs);
        jdbcTemplate.batchUpdate("INSERT INTO stage_metrics VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", stageArgs);
        
        System.out.println("Generated " + jobArgs.size() + " jobs, " + tableArgs.size() + " tables, " + stageArgs.size() + " stages.");
    }
}
