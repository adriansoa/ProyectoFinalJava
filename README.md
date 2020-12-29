# Biblioteca 2A
Este proyecto corresponde a la Biblioteca 2A de Java Avanzado. consiste en un abm de tablas utilizando Vert.x y JPA/Hibernate

## Compilador:

Para compilar el proyecto, ejecute en consola:

  ```
  mvn clean package
  ```
## Paquetes

Esta aplicacion contienen el paquede como un  _fat jar_, usando el 
[Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/).

## Ejecutando

Una vez compilado, simplemente inicie el  _fat jar_ de la siguiente manera:

```
java -jar target/api-rest-jpa-1.0-SNAPSHOT-fat.jar
```
Luego, inicie el navegador en el local host http://localhost:8080.
Allí se podrán visualizar los datos por el json siempre y cuando la BD se encuentra con datos en las tablas.


_**Integrantes;** Andrea Hospital y Adrian Soa_
