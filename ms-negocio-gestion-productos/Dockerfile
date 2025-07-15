FROM openjdk:21-jdk-slim
#Environment vars
#ENV USER_NAME=
#ENV USER_PASSWORD=
#ENV JAVA_OPTS="-Dspring.cloud.config.server.git.username=$USER_NAME -Dspring.cloud.config.server.git.password=$USER_PASSWORD"
LABEL build_date="05-07-2025"
LABEL autor="Galaxy Training"
LABEL name="MS Gestion Productos"
LABEL version="1.0"
LABEL description="Microservicio de gestion de productos"
VOLUME /tmp
EXPOSE 8081
COPY target/ms-negocio-gestion-productos*.jar ms-negocio-gestion-productos.jar
ENTRYPOINT ["java","-jar","/ms-negocio-gestion-productos.jar"]