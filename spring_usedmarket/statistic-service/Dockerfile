FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/statistic-service-1.0.jar StatisticService.jar
ENTRYPOINT ["java", "-jar", "StatisticService.jar"]