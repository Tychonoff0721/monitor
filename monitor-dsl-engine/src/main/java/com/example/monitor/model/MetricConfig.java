package com.example.monitor.model;

public class MetricConfig {
    private String id;
    private String description;
    private String dsl;
    private long frequencyMs; // Execution frequency
    private String analysisType; // e.g., "map", "trend"
    private long lastRunTime;

    public MetricConfig() {}

    public MetricConfig(String id, String description, String dsl, long frequencyMs, String analysisType, long lastRunTime) {
        this.id = id;
        this.description = description;
        this.dsl = dsl;
        this.frequencyMs = frequencyMs;
        this.analysisType = analysisType;
        this.lastRunTime = lastRunTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDsl() { return dsl; }
    public void setDsl(String dsl) { this.dsl = dsl; }

    public long getFrequencyMs() { return frequencyMs; }
    public void setFrequencyMs(long frequencyMs) { this.frequencyMs = frequencyMs; }

    public String getAnalysisType() { return analysisType; }
    public void setAnalysisType(String analysisType) { this.analysisType = analysisType; }

    public long getLastRunTime() { return lastRunTime; }
    public void setLastRunTime(long lastRunTime) { this.lastRunTime = lastRunTime; }
}
