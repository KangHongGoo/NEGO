FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/imgfile-service-1.0.jar ImgfileService.jar
ENTRYPOINT ["java", "-jar", "ImgfileService.jar"]