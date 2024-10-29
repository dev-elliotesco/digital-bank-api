# Banco Digital API

## Descripción
Este proyecto es un sistema de gestión de transacciones bancarias desarrollado con Spring WebFlux. 
La aplicación permite registrar usuarios, crear cuentas, realizar transacciones, 
y consultar información detallada de transacciones y balances en tiempo real.


## Requisitos & Tecnologías

- [Java 17](https://www.oracle.com/java/technologies/downloads/#java17?er=221886)
- [Gradle](https://gradle.org/)
- [Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Mongo DB](https://www.mongodb.com/es)
- [Docker](https://www.docker.com/)


## Instalación
Pasos para instalar las dependencias y el proyecto.

```bash
# Clona este repositorio
git clone https://github.com/dev-elliotesco/digital-bank-api.git

# Entra en el directorio del proyecto
cd digital-bank-api

# Compila el proyecto usando Gradle
./gradlew build

```


## Configuración

1. Antes de ejecutar el proyecto, asegúrate de tener una base de datos MongoDB en ejecución
   y de configurar las siguientes variables de entorno con las credenciales correctas de la base de datos:

- `PORT`: El puerto en el que se ejecutará la aplicación.
- `DB_HOST`: La dirección IP o el nombre de host de tu base de datos. Por ejemplo, `localhost`.
- `DB_NAME`: El nombre de la base de datos. Por ejemplo, `bank_db`.
- `DB_USER`: El nombre de usuario de tu base de datos. Por ejemplo, `root`.
- `DB_PASSWORD`: La contraseña de tu base de datos.

Por ejemplo, puedes definir las variables de entorno en tu sistema operativo o en tu IDE. Si estás
utilizando IntelliJ IDEA, puedes definir las variables de entorno en la configuración de tu
Run/Debug Configuration.

2. Opcional: puedes utilizar el archivo `docker-compose.yml`  proporcionado en la ruta `deployment/docker-compose.yml` 
para levantar la aplicación con la configuración por defecto y base de datos integrada.


## Ejecución
Pasos para ejecutar el proyecto.

### Localmente:

```bash
# Comando para iniciar el proyecto usando Gradle
./gradlew bootRun
```

```bash
# O ejecutando el JAR directamente
java -jar build/libs/digital-bank-0.0.1-SNAPSHOT.jar
```
Para generar el JAR:

```bash
# Generando el JAR
# Nota: Este comando compila el código, ejecuta las pruebas y genera el JAR
/gradlew build
```

### Docker (Solo servicio):

```bash
# Construye la imagen Docker
docker build -t digital-bank .

# Ejecuta el contenedor Docker
docker docker run -p 8080:8080 digital-bank
```

### Docker Compose (Servicio y base de datos):
```bash
# Ejecuta el docker-compose
docker-compose up
```

## Autor
- Elliot Escovitch - [Github](https://github.com/dev-elliotesco)
- [LinkedIn](https://www.linkedin.com/in/elliot-escovitch-580007205/)
- Correo electrónico: dev.elliot.escovitch@gmail.com

