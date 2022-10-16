# Ströer labs - interview task
Störer labs interview task. For more info visit https://www.ibillboard.com/uloha/vyvojar-uloha-1. 

App can be started on local machine or with Docker.

## Local setup

### Requirements:
- Java 18
- Maven 3.8.6
- Redis (local, remote, or Docker)

App uses Redis as a storage. If you haven't Redis installed, you can run it in Docker with:
```
docker run --rm --name redis -p 6379:6379 redis
```

Run app with:
```
mvn spring-boot:run
```

By default, app runs on port 8080. If you want to run it on another port, create file
`application-local.yml` in root directory and rewrite config properties which you want to change. Structure must be 
same as in application.yml. You can change other parameters as well.

To run tests, execute:
```
mvn test
```

## Docker setup
Alternatively, you can run whole app inside Docker containers. In this case, leave port settings to default (8080) or change 
EXPOSE port in Dockerfile. Exposed port must be same as port in config file.

Run Redis:
```
docker run --rm --name my-local-redis -p 6379:6379 redis
```

Then you have to set Redis host settings to be same as Redis image name in ```application-local.yml``` config file.
For example:
```
redis-conf:
  host: my-local-redis
```

Then build image with app:
```
docker build -t stroer-task .
```

After build, run app with:
```
docker run --rm -it -p 8080:8080 --link my-local-redis:redis -v "$(pwd)/output:/usr/local/lib/output" stroer-task:latest
```
Run command must contain link to running Redis image. Or alternatively must be in same network. Use volume if you want to 
see output file. Note: Be wary of mounting directory instead of file when using Windows!

When using Docker, tests are executed during build.

## Docker-compose setup
Alternatively, you can use docker-compose. In this case you have to set Redis host settings to be same as Redis image 
name in ```application-local.yml``` config file (like with Docker setup). After that, run app with:
```
docker-compose up
```