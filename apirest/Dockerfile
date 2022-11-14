FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} apirest-0.0.1.jar
ENTRYPOINT ["java","-jar","/apirest-0.0.1.jar"]