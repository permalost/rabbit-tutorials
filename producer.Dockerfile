FROM maven:alpine

WORKDIR /app
COPY . .
RUN mvn install

FROM openjdk:8-jre-alpine

WORKDIR /app
COPY --from=0 /app/target/producer-jar-with-dependencies.jar /app

CMD ["java", "-jar", "producer-jar-with-dependencies.jar"]
