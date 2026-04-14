FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/catalog-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]