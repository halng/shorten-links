FROM eclipse-temurin:21
COPY . .
WORKDIR /
RUN ./gradlew dependencies
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]