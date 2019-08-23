FROM maven:3-jdk-14 AS rabbit-tutorials-build

WORKDIR /src
ADD ./pom.xml .
RUN mvn package dependency:go-offline

COPY . .
RUN mvn package --offline

FROM openjdk:14-alpine

WORKDIR /app
COPY --from=rabbit-tutorials-build /src/target/consumer-jar-with-dependencies.jar /app

CMD ["java", "-jar", "consumer-jar-with-dependencies.jar"]
