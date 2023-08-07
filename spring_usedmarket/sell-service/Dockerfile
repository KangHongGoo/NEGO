FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/sell-service-1.0.jar SellService.jar
ENTRYPOINT ["java", "-jar", "SellService.jar"]