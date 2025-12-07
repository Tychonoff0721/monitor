package com.example.monitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/mock")
public class MockBigDataController {

    private final Random random = new Random();

    // 1. YARN Mock API
    @GetMapping("/yarn/ws/v1/cluster/apps")
    public Map<String, Object> getYarnApps(@RequestParam(required = false) String state) {
        List<Map<String, Object>> apps = new ArrayList<>();
        // Generate 5 mock apps
        for (int i = 0; i < 5; i++) {
            Map<String, Object> app = new HashMap<>();
            app.put("id", "application_123456_" + (1000 + i));
            app.put("name", "Spark_Job_" + i);
            app.put("state", state != null ? state : "RUNNING");
            app.put("finalStatus", "UNDEFINED");
            app.put("user", "hadoop");
            app.put("startedTime", System.currentTimeMillis() - random.nextInt(3600000));
            apps.add(app);
        }
        return Map.of("apps", Map.of("app", apps));
    }

    // 2. Spark Metrics Mock
    @GetMapping("/spark/api/v1/applications/{appId}/allexecutors")
    public List<Map<String, Object>> getSparkExecutors(@PathVariable String appId) {
        List<Map<String, Object>> executors = new ArrayList<>();
        // Mock driver and 2 executors
        executors.add(createExecutor("driver", 2048, 2));
        executors.add(createExecutor("1", 4096, 4));
        executors.add(createExecutor("2", 4096, 4));
        return executors;
    }

    private Map<String, Object> createExecutor(String id, int memory, int cores) {
        Map<String, Object> exec = new HashMap<>();
        exec.put("id", id);
        exec.put("totalInputBytes", random.nextLong() % 10000000L);
        exec.put("totalShuffleRead", random.nextLong() % 5000000L);
        exec.put("totalShuffleWrite", random.nextLong() % 5000000L);
        exec.put("maxMemory", memory * 1024L * 1024L);
        exec.put("totalTasks", 10 + random.nextInt(50));
        return exec;
    }

    // 3. Spark Stages (Plan) Mock - Simulating Tables
    @GetMapping("/spark/api/v1/applications/{appId}/stages")
    public List<Map<String, Object>> getSparkStages(@PathVariable String appId) {
        // We simulate a stage description that contains table scan info
        List<Map<String, Object>> stages = new ArrayList<>();
        
        Map<String, Object> stage0 = new HashMap<>();
        stage0.put("stageId", 0);
        stage0.put("name", "Scan parquet default.table_a"); // Hidden hint
        stage0.put("description", "FileScan parquet default.table_a ... location: hdfs://mrs1/user/hive/warehouse/table_a");
        stages.add(stage0);

        Map<String, Object> stage1 = new HashMap<>();
        stage1.put("stageId", 1);
        stage1.put("name", "Scan parquet default.table_b");
        stage1.put("description", "FileScan parquet default.table_b ... location: hdfs://mrs1/user/hive/warehouse/table_b");
        stages.add(stage1);

        return stages;
    }

    // 4. HMS Mock
    @GetMapping("/hms/table/{db}/{table}")
    public Map<String, Object> getTableStats(@PathVariable String db, @PathVariable String table) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("dbName", db);
        stats.put("tableName", table);
        stats.put("totalSize", 1024L * 1024L * (100 + random.nextInt(900))); // 100MB - 1GB
        stats.put("numFiles", 10 + random.nextInt(50));
        stats.put("format", "parquet");
        return stats;
    }
    
    // 5. Generic JMX Mock
    @GetMapping("/jmx")
    public Map<String, Object> getJmx(@RequestParam(required = false) String qry) {
        // Mimic Hadoop JMX response structure: { "beans": [ ... ] }
        List<Map<String, Object>> beans = new ArrayList<>();
        
        Map<String, Object> bean = new HashMap<>();
        // If query is for NameNode RpcActivity
        if (qry != null && qry.contains("RpcActivity")) {
            bean.put("name", "Hadoop:service=NameNode,name=RpcActivity");
            bean.put("CallQueueLength", random.nextInt(100));
            bean.put("RpcProcessingTimeAvgTime", random.nextDouble() * 10.0);
        } else {
            // Default generic bean
            bean.put("name", "Hadoop:service=NameNode,name=FSNamesystem");
            bean.put("CapacityUsed", random.nextLong() % 1000000000L);
            bean.put("FilesTotal", 5000 + random.nextInt(1000));
        }
        beans.add(bean);
        
        return Map.of("beans", beans);
    }
}
