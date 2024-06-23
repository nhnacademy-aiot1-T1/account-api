FROM openjdk:11-jre
COPY target/*.jar account.jar
ENTRYPOINT ["java", "-jar", "account.jar"]