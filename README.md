# Bridge-i: AS/400 (IBM i) to Cloud ETL Pipeline 🌉

An enterprise-grade, high-performance ETL pipeline built with Spring Boot and Spring Batch. This application safely extracts legacy data from an IBM i (AS/400) DB2 database, transforms outdated data structures, and loads them into a modern PostgreSQL cloud environment.

## Technical Architecture
* **Source System:** IBM i (AS/400) DB2 accessed via JTOpen JDBC.
* **Orchestration:** Spring Batch 5 (Chunk-based processing for memory-safe extraction).
* **Target System:** PostgreSQL (Containerized via Docker).
* **Connection Management:** HikariCP configured specifically to protect AS/400 interactive jobs.

## Key Features
* **Legacy Data Transformation:** Automatically converts standard AS/400 integer-based dates (YYYYMMDD) into modern ISO-8601 `LocalDate` formats.
* **Memory Safe:** Implements cursor-based reading and chunk-based writing to handle tables with millions of rows without overwhelming the JVM.
* **Infrastructure as Code:** Includes a `docker-compose.yml` for instant, local PostgreSQL provisioning.

## How to Run Locally
1. Ensure Docker Desktop is running.
2. Spin up the target database: `docker-compose up -d`
3. Update `application.yml` with your AS/400 credentials.
4. Run the application: `./mvnw spring-boot:run`

---
*Built by a CodesbyYudi|Backend Developer|Specializing in legacy modernization and robust data pipelines.*
*Location: Noida | Available for freelance architecture and modernization engagements.*
