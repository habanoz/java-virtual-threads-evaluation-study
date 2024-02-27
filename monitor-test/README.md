# Containers

Docker compose file is useful for testing and monitoring.

Run following command to launch all services.
```bash
docker-compose up --build
```

## Monitoring

Prometheus is configured to collect performance metrics from the spring boot application.

```bash
docker-compose up -d prometheus grafana
```

Open grafana at `http://localhost:3000/` and select load micrometer dashboard. It will display live results.

Monitoring can be valuable at any time. 

## Load Testing
K6 is configured for load testing. scripts directory contains test scripts.
Influxdb keeps metrics about the testing.
Grafana can be used to monitor test results.

```bash
docker-compose up -d influxdb grafana
docker-compose run k6 run /scripts/query_user.js
```

Open grafana at `http://localhost:3000/` and select load testing dashboard. It will display live results. k6 will also print results to console once test is over.

## Reset

Use provided script to clear past monitoring data.

```bash
docker-compose up -d influxdb grafana
docker-compose run k6 run /scripts/query_user.js
```
