FROM openjdk:8-slim
#env
ENV LOG_LEVEL INFO
#copy jar from target to image
ADD target/salary-1.0-SNAPSHOT.jar /usr/src/salary-1.0-SNAPSHOT.jar
#set work dir to jar-file path
WORKDIR /usr/src
#port expose
EXPOSE 9090
#start jar inside container
ENTRYPOINT java -Dlogging.level.ru.docker=$LOG_LEVEL -Dspring.flyway.url=$JDBC_URL -Dspring.flyway.user=$JDBC_USER -Dspring.flyway.password=$JDBC_PASS -jar salary-1.0-SNAPSHOT.jar