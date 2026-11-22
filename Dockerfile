ARG JAVA_VERSION=27
FROM eclipse-temurin:${JAVA_VERSION}-jdk AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -ntp dependency:go-offline
COPY src src
RUN ./mvnw -B -ntp package -DskipTests -Dspotless.check.skip=true

FROM eclipse-temurin:${JAVA_VERSION}-jre
WORKDIR /app
RUN useradd -r app
COPY --from=build /app/target/*.jar app.jar
USER app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]