FROM maven:3.8.1-jdk-11
WORKDIR /app
COPY ./cooking-project /app

RUN mvn package -Dmaven.test.skip
CMD java -jar target/cooking-project-0.0.1-SNAPSHOT.jar
