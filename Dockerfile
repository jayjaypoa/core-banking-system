FROM openjdk:11-jre-slim
EXPOSE 8080
ADD /build/libs/core-0.0.1-SNAPSHOT.jar /opt/core-banking-system.jar
CMD exec java -jar /opt/core-banking-system.jar