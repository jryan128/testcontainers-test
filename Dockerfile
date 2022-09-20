FROM eclipse-temurin:17-jdk
RUN mkdir /opt/app
COPY . /opt/app
WORKDIR /opt/app
RUN ./gradlew test