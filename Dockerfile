# === Stage 1: Build with Maven ===
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 複製 pom.xml 並下載依賴（快取機制）
COPY pom.xml .
RUN mvn dependency:go-offline

# 複製專案原始碼並打包
COPY src ./src
RUN mvn clean package -DskipTests

# === Stage 2: Run with JDK ===
FROM openjdk:17-jdk-slim
WORKDIR /app

# 從上個 stage 拷貝 jar
COPY --from=build /app/target/Summer2025-1.0-SNAPSHOT.jar app.jar

# 啟動 Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
