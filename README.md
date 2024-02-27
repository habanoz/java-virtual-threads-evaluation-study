## Virtual Threads Evaluation Study

[My blog post on this study.](https://habanoz.github.io/tech-posts/analysis/java-virtual-threads-benchmark-comparison/)

### Launch Application

Run synchronous application. 
```bash
./run-docker-compose.sh sync
```

Run reactive application.
```bash
./run-docker-compose.sh reactive
```

Enable virtual threads by setting VT_ENABLE variable in docker compose file to true. Then run synchronous application.
```bash
./run-docker-compose.sh sync
```

### Test

See [Monitoring](monitor-test/README.md) page.

Simply run following command to launch monitoring tools and start testing script:

```bash
cd monitor-test
docker-compose up --build
docker-compose run k6 run /scripts/register_user.js
```
