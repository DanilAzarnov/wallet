FROM maven:3-jdk-11-slim AS builder
COPY . /usr/src/wallet
WORKDIR /usr/src/wallet
RUN mvn clean package

FROM openjdk:11-jre-slim
COPY --from=builder /usr/src/wallet/target/wallet.jar /usr/bin/wallet.jar
ENTRYPOINT java -jar /usr/bin/wallet.jar