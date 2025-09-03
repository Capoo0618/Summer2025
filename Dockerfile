# 使用官方 OpenJDK 作為基底
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 將 Maven 打包好的 jar 複製進容器
COPY target/Summer2025-1.0-SNAPSHOT.jar app.jar

# 設定容器啟動指令
ENTRYPOINT ["java", "-jar", "app.jar"]

# Railway / Render 會自動給 PORT 環境變數
EXPOSE 8080
