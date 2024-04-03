FROM openjdk:22
EXPOSE 8080
#ADD target/*.jar spring-boot-docker.jar
COPY target/*.jar spring-boot-docker.jar
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]