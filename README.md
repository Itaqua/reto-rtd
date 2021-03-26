# reto-rtd
La intensión del proyecto es crear una API para calcular el sueldo de los jugadores de Resuelve FC.

El proyecto esta realizado con el siguiente stack:
    
    - VCS: git (github)
    - Spring Boot: framework web para construir aplicaciones reactivas y MVC usando la JVM
    - Groovy: lenguaje dinámico que extiende la JVM inspirado en Ruby

## Metodo de trabajo
La forma de trabajar esta fuertemente inspirada en Gitflow (https://danielkummer.github.io/git-flow-cheatsheet/), creando Issues en el repositorio de Github (https://github.com/Itaqua/reto-rtd/issues?q=is%3Aissue+is%3Aclosed) y de ahi se van creando ramas (branches) de trabajo donde se van creando los **commits** que al final se crea un **Pull Request** que se une con la rama principal (**main**).

Por ejemplo, los primeros issues son crear el placeholder del proyecto, despues la integración continua (CI) y posteriormente implementar las Pruebas de las reglas del negocio que serán posteriormente implementadas.

Además de esto, se crearón **actions** (https://github.com/Itaqua/reto-rtd/actions) para que cada vez que se agrega mergea o se agrega un cambio en la rama principal, se solicita hacer un build automático y liberación eh *Heroku*: https://reto-rtd.herokuapp.com/

## Configuración inicial
Para ejecutar este proyecto se debe de tener la maquina virtual de Java versión 8.0 o superior, esta se puede instalar de varias formas dependiendo del sistema en el que se este trabajando.

// Otra forma es utilizando Docker, como se verá mas adelante en la sección correspondiente

### Instalar la JVM
Si se tiene un sistema Linux o Mac y experiencia usando package managers, se recomienda usar SDKMAN (https://sdkman.io/) que permite una fácil gestoria de recursos de la JVM entre distintas versiones.

En caso de sistemas Windows o para una instalación más limpia, se recomienda usar adoptopenjdk que es un empaquetador de versiones de Java administrado por la comunidad:
https://adoptopenjdk.net/installation.html

### VCS: Instalar Git
El proyecto utiliza un Sistema de control de Versiones utilizando git a través de Github.

Para instalar la herramienta, siga el proceso de instalación y descarga de la herramienta que requiera:

https://git-scm.com/downloads
### Github: Descargar el código
Obtener el codigo desde el repositorio de Github:
```
git clone git@github.com:Itaqua/reto-rtd.git
```

## Estructura del código
LA aplicación es un clásico MVC que actualmente solo utiliza la parte de los controladores para responder a las llamadas HTTP y los servicios como una la abstración de la lógica de negocio agnostica al los metodos de ejecución.

```
main/
    groovy/com/rtd/fc/
        controllers
            FC.groovy
        services
            PayrollService.groovy
        FCApplication.groovy
    resources
        static
            app.css
            index.html
        application.properties
test/groovy/com/rtd/fc/service
        PayrollServiceTest.groovy
build.gradle
```

El **controller/FC.groovy** es el que contiene el endpoint para responder a las llamadas POST al API.

El **services/PayrollService.groovy** contiene la lógica de negocio para la ejecución de los servicios.

**FCApplication.groovy** es el punto de inicio de la aplicación inyectando los servicios utilizando Injectión de Dependencias (DI).

**resources/static** contiene archivos estaticos servidos por la aplicación, como la forma para probar el API.

**application.properties** no se usa en este momento, pero es para proporcionar distintas configuraciones y hasta perfiles de configuración.

**PAyrolServiceTest** contiene los test unitarios donde se documenta y comprueba las reglas de negocio.

**build.gradle** es el build file, algo así como un **make** (o **package.json** para los mas jovenes) para construir la aplicación.
### Correr la aplicación
Para correr la aplicación, se realiza utilizando el sistema de empaquetado llamado Gradle:
```
./gradlew bootRun
```

### Empaquetar la aplicación (JAR)
La aplicación se puede empaquetar en un fat jar (paquete con todas sus dependencias) usando el comando:
```
./gradlew build
```
esto generará el archivo en **build/lib** por ejemplo *fc-0.0.1-SNAPSHOT.jar*

este archivo puede posteriormente ejecutarse usando la JVM con el siguiente comando:
```
java -jar fc-0.0.1-SNAPSHOT.jar
```
