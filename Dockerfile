FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/coffee-shop-app-1.0-SNAPSHOT.jar coffee-shop-app.jar
ENTRYPOINT ["java", "-jar", "/coffee-shop-app.jar"]