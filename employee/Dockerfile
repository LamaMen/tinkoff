FROM openjdk:8-slim
#env
ENV LOG_LEVEL INFO
#copy jar from target to image
ADD target/employee-1.0-SNAPSHOT.jar /usr/src/employee-1.0-SNAPSHOT.jar
#set work dir to jar-file path
WORKDIR /usr/src
#port expose
EXPOSE 8080
#start jar inside container
ENTRYPOINT java -Dlogging.level.ru.docker=$LOG_LEVEL -Dservice.salary.url=$SALARY_URL -Dspring.flyway.url=$JDBC_URL -Dspring.flyway.user=$JDBC_USER -Dspring.flyway.password=$JDBC_PASS -jar employee-1.0-SNAPSHOT.jar