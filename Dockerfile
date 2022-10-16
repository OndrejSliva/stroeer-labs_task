FROM maven:3.8.6-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY application-local.yml /home/app
WORKDIR /home/app
RUN mvn -f pom.xml clean package


FROM openjdk:18.0.2.1-jdk-oracle
COPY --from=build /home/app/target/stroeer-labs_task-1.0-SNAPSHOT.jar /usr/local/lib/app.jar
COPY application-local.yml /usr/local/lib
WORKDIR /usr/local/lib
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]