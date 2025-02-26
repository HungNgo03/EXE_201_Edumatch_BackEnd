FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/FindTutor-0.0.1-SNAPSHOT.jar /app/FindTutor.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "FindTutor.jar"]