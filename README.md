# BigDataOps Monitor

## Project Structure

- `monitor-dsl-engine`: Backend (Spring Boot) - Handles API, DSL execution, and Database interaction.
- `monitor-ui`: Frontend (React + Vite) - Node.js web service for the dashboard.

## Prerequisites

- Java 21+
- Maven
- Node.js 18+
- MySQL (Optional, defaults to H2 if configured, but currently configured for MySQL)

## How to Run

### 1. Backend (monitor-dsl-engine)

The backend provides the REST API and runs the DSL engine.

```bash
cd monitor-dsl-engine
mvn spring-boot:run
```

- API runs at: `http://localhost:8080`
- Swagger/OpenAPI: `http://localhost:8080/swagger-ui.html` (if enabled)

### 2. Frontend (monitor-ui)

The frontend is a React application built with Vite.

```bash
cd monitor-ui
npm install
npm run dev
```

- Frontend runs at: `http://localhost:5173` (by default)
- It proxies `/api` requests to `http://localhost:8080`.

## Features

- **Slow Job Analysis**: Visualize job performance, shuffle data, and storage usage.
- **Monitor Config**: Dynamic metric configuration using the DSL.
    - Supports configurable frequency (e.g., 30s, 1m).
    - Supports different analysis types (Map, Trend).
- **DSL Engine**:
    - `METRIC ... ON ... AS ...` syntax.
    - Supported Functions: `jmx_func`, `sum`, `extract_tables`, etc.

## Database

The application automatically initializes the following tables (in `MockDataGenerator`):
- `job_metrics`: Job execution stats.
- `stage_metrics`: Stage-level details.
- `table_metrics`: Table usage stats.
- `instances`: Cluster instance metadata.
- `endpoint_templates`: JMX endpoint templates.
- `metric_configs`: User-defined monitoring rules.

*Note: The current configuration resets the database on every restart (Mock Mode).*
