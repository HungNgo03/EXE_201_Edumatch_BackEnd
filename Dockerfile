# Sử dụng image chính thức của OpenJDK làm base image
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR vào container
COPY target/FindTutor-0.0.1-SNAPSHOT.jar /app/

# Chạy ứng dụng khi container bắt đầu
CMD ["java", "-jar", "FindTutor-0.0.1-SNAPSHOT.jar"]
