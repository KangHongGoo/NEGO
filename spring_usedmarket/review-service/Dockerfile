FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/review-service-1.0.jar ReviewService.jar
ENTRYPOINT ["java", "-jar", "ReviewService.jar"]