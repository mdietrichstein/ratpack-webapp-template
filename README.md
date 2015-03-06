# Ratpack Webapp Template
A basic web application template using java8 and modern libraries.

## Libraries
* Web Serving: [Ratpack](http://www.ratpack.io)
* Dependency Injection: [Guice](https://github.com/google/guice)
* Configuration: [Aeonbits Owner](http://owner.aeonbits.org)
* Logging: [SL4J](http://www.slf4j.org),  [Logback](http://logback.qos.ch)

## Starting

```shell
./gradlew runApp
```
Accessing [http://localhost:5050](http://localhost:5050) serves files located in [src/main/resources/webapp](src/main/resources/webapp).

[http://localhost:6060/api/v1/hello](http://localhost:6060/api/v1/hello) serves a dummy api response defined in [HelloWorldApiHander](src/main/java/template/web/ratpack/handler/api/HelloWorldApiHander.java).

## Configuration

You can directly configure the properties defined in [src/main/resources/default.properties](src/main/resources/default.properties).

Alternatively you can create an _app.conf_ file somewhere and pass the directory containing the config file via the _configDir_ property to the gradle _runApp_ task:

```shell
./gradlew runApp -PconfigDir=./conf/
```
app.conf properties override properties defined in [src/main/resources/default.properties](src/main/resources/default.properties).

Logging can be configured via [src/main/resources/logback.xml](src/main/resources/logback.xml) or by placing a custom _logback.xml_ in the _configDir_ directory mentioned above.