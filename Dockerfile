FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]