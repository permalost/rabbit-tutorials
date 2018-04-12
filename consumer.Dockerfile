FROM maven:alpine

WORKDIR /app
COPY . .
RUN mvn install

FROM openjdk:8-jre-alpine

WORKDIR /app
COPY --from=0 /app/target/consumer-jar-with-dependencies.jar /app

CMD ["java", "-jar", "consumer-jar-with-dependencies.jar"]
