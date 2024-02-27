# Containers

Docker compose file is useful for testing and monitoring.

Run following command to launch all services.

```bash
docker-compose up --build
```

## Monitoring

Open grafana at `http://localhost:3000/` and select a dashboard.

**JVM Dashboard:**

http://localhost:3000/d/9Qw7GYoIz/jvm-micrometer

**K6 Dashboard:**

http://localhost:3000/d/k6/k6-load-testing-results



## Load Testing
K6 is configured for load testing. scripts directory contains test scripts.
Influxdb keeps metrics about the testing.

```bash
docker-compose run k6 run /scripts/register_user.js
```

## Reset

Use provided script to clear past monitoring data.

```bash
./stop-cleanup.sh
```
