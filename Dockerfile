FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/FindTutor-0.0.1-SNAPSHOT.jar FindTutor.jar
ENTRYPOINT ["java","-jar","/FindTutor.jar"]
