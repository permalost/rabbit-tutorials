FROM maven:alpine AS rabbit-tutorials-build

WORKDIR /app
ADD ./pom.xml /src/pom.xml
WORKDIR /src
RUN mvn verify clean --fail-never

COPY . .
RUN mvn package

FROM openjdk:8-jre-alpine

WORKDIR /app
COPY --from=rabbit-tutorials-build /src/target/consumer-jar-with-dependencies.jar /app

CMD ["java", "-jar", "consumer-jar-with-dependencies.jar"]
