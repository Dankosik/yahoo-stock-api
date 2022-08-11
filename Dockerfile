FROM openjdk:17
EXPOSE 8080
ADD build/libs/yahoo-stock-api.jar yahoo-stock-api.jar
ENTRYPOINT ["java","-jar","/yahoo-stock-api.jar"]