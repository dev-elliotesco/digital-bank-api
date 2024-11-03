# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY build/libs/digital-bank-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]