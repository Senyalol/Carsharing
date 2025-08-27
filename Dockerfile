FROM maven:3.9.9-eclipse-temurin-23 AS build

ENV POSTGRES_USERNAME=postgres
ENV POSTGRES_PASSWORD=1111
ENV POSTGRES_DB=semadb
ENV POSTGRES_HOST=postgres
ENV POSTGRES_PORT=5432

WORKDIR /build

COPY ShortCarInfoDTO .

RUN mvn clean install

WORKDIR /Uservice

COPY Carsharing .

RUN mvn clean install -DskipTests

FROM eclipse-temurin:23-jre-alpine

#ENV POSTGRES_USERNAME=postgres
#ENV POSTGRES_PASSWORD=1111
#ENV POSTGRES_DB=semadb
#ENV POSTGRES_HOST=postgres
#ENV POSTGRES_PORT=5432

WORKDIR /app

COPY --from=build /Uservice/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]