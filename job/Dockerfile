FROM maven:3.8.2-jdk-8 as build
WORKDIR /app
COPY . /app
RUN mvn install

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/job-1.0-SNAPSHOT.jar /app
CMD ["java -jar job-1.0-SNAPSHOT.jar"]