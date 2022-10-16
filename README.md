# Ströer labs - interview task

### Requirements:
- Java 18
- Maven 3.8.6
- Redis (or Docker)

App uses Redis as a storage. If you haven't Redis installed, you can run it in Docker with:
```
docker run --name redis -p 6379:6379 --network=redis redis
```

Run app with:
```
mvn spring-boot:run
```

By default, app runs on port 8080. If you want to run it on another port, create file
`application-local.yml` in root directory and rewrite config properties. You can change
other parameters as well.

To run tests execute:
```
mvn test
```