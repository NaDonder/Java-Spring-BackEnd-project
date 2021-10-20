FROM openjdk:16-alpine
COPY ./target/CouponProject-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch CouponProject-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","jar","CouponProject-0.0.1-SNAPSHOT.jar"]