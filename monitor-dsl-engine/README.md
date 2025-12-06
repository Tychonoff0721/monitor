# Spark on YARN Monitor Backend

This project implements the backend for a Big Data Job Monitoring System based on the provided DSL architecture.

## Project Structure

- `monitor-dsl-engine`: The core DSL engine and monitoring application.
  - `src/main/java/com/example/dsl`: The generic DSL engine components (AST, Execution, Metadata).
  - `src/main/java/com/example/monitor`: The specific monitoring application implementation.

## Features

- **Scalability**: Designed to handle 2000 nodes and 1000 concurrent jobs using efficient batch processing.
- **Cycle**: Runs every 5 minutes (configurable).
- **Filtering**: Captures long-running jobs (> 30 mins).
- **Data Collection**:
  - Spark Metrics (Output, Shuffle)
  - Table Lineage (Input/Output tables)
  - HDFS/HMS Metadata (Space, File Count)
- **Storage**: Persists data to RDBMS (configured for H2, compatible with MySQL/PostgreSQL) using Batch Upserts.

## Prerequisites

- Java 11+
- Maven 3.6+

## Building and Running

1. **Build the project**:
   ```bash
   mvn clean package
   ```

2. **Run the Monitor Application**:
   ```bash
   java -cp target/monitor-dsl-engine-1.0-SNAPSHOT.jar com.example.monitor.MonitorApplication
   ```

## Architecture Notes

The system uses the `monitor-dsl-engine` architecture:
- **ExecutionEnv**: Resolves endpoints (YARN, Spark, HDFS) dynamically.
- **Functions**: Encapsulate logic for fetching metrics (`yarn_running_apps`, `spark_app_details`, etc.).
- **Pipeline**: The monitoring cycle executes a pipeline of functions to gather and enrich data.

The `MonitorApplication` constructs the execution pipeline programmatically to ensure type safety and performance for the backend service, while maintaining the flexibility to switch to DSL script parsing in the future.
