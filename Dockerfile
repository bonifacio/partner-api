FROM openjdk:12
WORKDIR /opt/partner
COPY build/libs/partner*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]