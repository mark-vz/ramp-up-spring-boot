# Ramp-Up: Spring Boot and more

Note: This document is currently work in progress.

[![CI with Gradle](https://github.com/mark-vz/ramp-up-spring-boot/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/mark-vz/ramp-up-spring-boot/actions/workflows/build.yml)

## Topics

### Project scaffolding

Der einfachste Weg, einen frischen Spring Boot Service zu erstellen, ist der _Spring Initializr_:

<https://start.spring.io/>

Auf dieser Seite konfigurierst du den Namen deines neu zu erstellenden Services, ein paar grundlegende Projekteigenschaften sowie benötigte Module (Abhängigkeiten). Anschließend klickst du auf den "Generate" Button und die fertige "nackte" Spring Boot Anwendung wird heruntergeladen.

Spring Boot liefert für viele Anwendungsfälle und Anforderungen bereits fertige Dependencies. Ein Web Service, so wie er vielfach bei der REWE digital zum Einsatz kommt, benötigt oft folgende Abhängigkeiten:

- Spring Boot DevTools
- Spring Web
- Spring Security
- Spring Data JPA
- Liquibase Migration
- Validation
- Spring Boot Actuator
- Prometheus
- Testcontainers
- Resilience4J

Welche Abhängigkeiten tatsächlich nötig sind, hängt natürlich vom konkreten Einzelfall ab. In der Regel verwenden die Services noch einige weitere Abhängigkeiten.

Der folgende Link führt dich zu einer beispielhaften Demo-Konfiguration mit nur den Abhängigkeiten "Spring Boot DevTools" und "Spring Web":

<https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.6.3&packaging=jar&jvmVersion=17&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=devtools,web>

### Gradle

Als Build-Werkzeug verwenden wir _Gradle_ mit _Groovy_. Beides ist auch die Voraussetzung für die weiteren Abschnitte.

Mehr unter:

- <https://docs.gradle.org/current/userguide/getting_started.html>
- <https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/>
- <https://www.baeldung.com/spring-boot-gradle-plugin>

### Run

Nachdem du deine Spring Boot Anwendung heruntergeladen hast, kannst du das Zip-File auspacken und die Anwendung mittels

```bash
./gradlew bootRun
```

starten. Da die Applikation noch nie gebaut wurde, geht dem Start automatisch ein Build voran.

Im Browser unter <http://localhost:8080/> antwortet der noch nicht sonderlich sinnvolle Service (korrekterweise) mit einer "Whitelabel Error Page", da es noch keine Endpunkte gibt. Erster Durchstich geschafft ... :-)

### Build

Jede Spring Boot Anwendung enthält ein Build File, welches alle benötigten Abhängigkeiten (z.B. die oben gelisteten) und Build-Konfigurationen definiert. Je nach Vorliebe liegt ein Maven oder Gradle Build File vor. Letzteres kann in Groovy oder Kotlin Script geschrieben sein.

Neben der grundlegenden Konfiguration werden im Build File auch _Tasks_ definiert. Schlägt ein Task fehl, so schlägt der gesamte Build fehl. Ein Standard-Task ist beispielsweise die Ausführung sämtlicher Tests.

Der folgende Befehl stößt den Build-Vorgang an:

```bash
./gradlew build
```

### Testing

Wir schreiben unsere Unit- und Integrationstests mithilfe des _Spock_ Frameworks. Die verwendete Sprache ist Groovy, allerdings sind keine tiefer gehenden Groovy-Kenntnisse nötig, um Tests schreiben zu können. ;)

Während Unit-Tests einzelne, gekapselte Funktionalitäten testen und alle äußeren Abhängigkeiten "gemocked" werden, benötigen Integrationstests die komplette Spring Umgebung, ggf. eine Datenbank, weitere Services, etc., um getestet werden zu können. Mittels _Testcontainers_ lässt sich eine vollständige Umgebung mit z.B. einem Datenbanksystem modular als Docker-Container hochfahren gegen die dann getestet wird. Die Testumgebung wird im Test-Code programmatisch konfiguriert.

REST-Endpunkte können in einem Integrationstest komfortabel mit _REST Assured_ getestet werden.

Mehr unter:

- <https://spockframework.org/>
- <https://www.baeldung.com/groovy-spock>
- <https://groovy-lang.org/>
- <https://www.testcontainers.org/>
- <https://www.baeldung.com/docker-test-containers>
- <https://www.baeldung.com/spring-boot-testcontainers-integration-test>
- <https://rest-assured.io/>
- <https://www.baeldung.com/rest-assured-response>

### Test coverage

Die Test-Coverage sagt uns detailliert, welche Teile des Codes (Klassen, Methoden, Zeilen, Verzweigungen) wie gut getestet sind. Wir streben eine Test-Coverage von mindestens 80% Prozent an. _JaCoCo_ ist ein Tool, welches uns die Test-Coverage unseres Java-Codes ermittelt. Seine Ausführung ist Teil des Build-Prozesses und wird im Build File konfiguriert.

Mehr unter:

- <https://www.baeldung.com/jacoco>
- <https://www.jacoco.org/jacoco/trunk/index.html>

### REST

In Spring Boot werden ein oder mehrere Endpunkte in einem sogenannten _Controller_ zusammen gefasst. Obschon man theoretisch komplett frei in der Organisation und Benamung seiner Controller-Klassen und Endpunkte ist, folgen wir den Vorschlägen aus dem RESTful API-Design:

- Zentrales Konzept sind _Resources_ (z.B. die Menge der Nutzer)
- eine Ressource wird immer durch ein Nomen ausgedrückt (z.B. `User`)
- API-Pfade enthalten eine Ressource im plural, wenn es mehrere sein können (z.B. `/api/users/<id-des-users>`)
- eine Controller-Klasse ist nach ihrer Ressource (immer singular) benannt (z.B. `UserController`)
- ein Controller kümmert sich nie mehr als um eine Ressource
- Controller stellen nach Möglichkeit nur CRUD-Aktionen (Create, Read, Update oder Delete) zur Verfügung (z.B. `getAllUsers(...)`)
- Aktionen (Create, Read, Update oder Delete) werden über HTTP-Verben und nie via Pfad-Artefakte gesteuert (z.B. `DELETE /api/users/<id-des-users>`)

Mehr unter:

- <https://restfulapi.net/>
- <https://spring.io/guides/tutorials/rest/>
- <https://www.baeldung.com/rest-with-spring-series>

### OpenAPI / Swagger

Es ist immer sinnvoll alle Endpunkte zu dokumentieren. Dies lässt sich mittels der _OpenAPI Specification_ automatisieren. Über entsprechende Java _Annotations_ im Controller steuert man die Erstellung. Die Dokumentation ist dann über einen öffentlichen Endpunkt (der Pfad ist konfigurierbar) im Browser einsehbar.

Mehr unter:

- <https://swagger.io/specification/>
- <https://www.baeldung.com/spring-rest-openapi-documentation>

### Controller, Service, Repository

In Spring Boot stellt ein sogenannter _Controller_ (z.B. `UserController`) einen oder mehrere Endpunkte (z.B. `/api/users`) zur Verfügung. Business-Logik sollte nicht in Controllern definiert werden. Controller können einen oder mehrere sogenannte _Services_ aufrufen. Ein Service kapselt die eigentliche Business-Logik. Dies können z.B. auch Anfragen an eine Datenbank sein, welche wiederum nicht direkt im Service stattfinden, sondern über ein sogenanntes `Repository` delegiert werden. In einem Repository werden eine oder mehrere DB-Anfragen formuliert. In der Regel geschieht dies via _Spring Data JPA_ (siehe Abschnitt unten). Neben der Kommunikation mit einem Repository können Services auch weitere Services aufrufen.

Controller-, Service- und Repository-Instanzen werden _Spring Beans_ genannt. Neben diesen drei Ausprägungen gibt es noch weitere Bean-Typen.

Mehr unter:

- <https://www.baeldung.com/spring-bean>
- <https://spring.io/guides/tutorials/rest/>
- <https://spring.io/guides/gs/rest-service/>
- <https://www.baeldung.com/spring-controllers>

### Dependency Injection / Inversion of Control

Das Spring Framework - und somit Spring Boot - unterstützt die sogenannte _Dependency Injection_. Mit ihrer Hilfe können _Spring Beans_ vom Spring Framework automatisch instantiiert und an Klassen, die eine Abhängigkeit zu ihnen haben, übergeben werden.

Mehr unter:

- <https://www.baeldung.com/spring-dependency-injection>
- <https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring>
- <https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-spring-beans-and-dependency-injection.html>

### Spring Data JPA

_Spring Data JPA_ erlaubt es, einfache DB-Queries statt mittels SQL über entsprechend benamte Methoden auszudrücken. Das folgende Repository (ein Spring Bean über das DB-Zugriffe stattfinden) stellt eine Methode zur Verfügung, welche alle User (Tabelle `myusers`, Spalten `email_address` und `lastname`) zurückgibt. Die normierten Methodennamen folgen in ihrem Aufbau spezifischen Regeln und Konventionen und lassen sich mit etwas Übung in die entsprechende SQL-Query übersetzen:

```java
@Entity
@Table(name = "myusers")
public interface UserRepository extends Repository<User, Long> {
    // select * from myusers where email_address='<value of emailAddress>' and lastname='<value of lastname>';
    List<User> findByEmailAddressAndLastname(String emailAddress, String lastname);
}
```

Wenn die gewünschte Query komplexer wird oder man einen programmatischen Ansatz bevorzugt, lassen kann man die Query auch mittels der `@Query` Annotation erstellen:

```java
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
  User findByLastnameOrFirstname(@Param("lastname") String lastname, @Param("firstname") String firstname);
}
```

Mehr unter:

- <https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query>
- <https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa>
- <https://www.baeldung.com/jpa-entity-table-names>

### Liquibase

Spring Boot bietet Unterstützung für alle gängigen Datenbanksysteme. Sämtliche Änderungen am Datenbankschema sollten genauso Teil des Sourcecode-Managements sein, wie der Quellcode selbst. _Liquibase_ hilft nicht nur beim initialen Anlegen aller Tabellen und Seed-Daten sondern ermöglicht auch die Schrittweise Ergänzung, Änderung, Entfernung von Tabellen, Indizes, Daten, etc. - die sogenannte Schema-Evolution. Dabei wird jeder einzelne Schritt der gesamten Historie in einem eigenen File festgehalten und unter Git-Kontrolle gebracht.

Mehr unter:

- <https://liquibase.org/>
- <https://docs.liquibase.com/tools-integrations/springboot/using-springboot-with-maven.html>
- <https://www.baeldung.com/liquibase-refactor-schema-of-java-app>

### Security

Die REST-Endpunkte aller Services sind in aller Regel nicht öffentlich. Sie sind mittels _OAuth2_ abgesichert. Das heißt, dass sie nur aufgerufen werden können, wenn ein gültiges Token (_JWT_) im `Authorization` Header des Requests mitgeschickt wird. In der REWE digital werden die Tokens von einem zentralen Authorization Server angefordert und sind nur kurze Zeit gültig. Der Authorization Server ist nur im VPN erreichbar und via Basic Auth abgesichert.

Es wird der _Authorization Code Flow_ verwendet.

Mehr unter:

- <https://auth0.com/docs/get-started/authentication-and-authorization-flow/authorization-code-flow>
- <https://jwt.io/>
- <https://www.baeldung.com/spring-security-oauth>
- <https://www.baeldung.com/spring-security-oauth-auth-server>

### Resilience

#### Bulkhead

Micro-Services sollten das _Bulkhead_ Pattern implementieren. Damit wird verhindert, dass ein Fehler bei der Bearbeitung eines einzelnen eingehenden Requests Auswirkungen auf die Bearbeitung anderer eingehender Requests hat. Ein Bulkhead schützt den eigenen Service und verrichtet seine Arbeit während des Empfangs von Daten (Empfang von Requests von anderen Services).

Mehr unter:

- <https://resilience4j.readme.io/docs/bulkhead>
- <https://www.vinsguru.com/bulkhead-pattern/>

#### Circuit Breaker

Micro-Services sollten das _Circuit Breaker_ Pattern implementieren. Damit wird sichergestellt, dass keine Requests mehr an andere Services gestellt werden, die akut mit der Bearbeitung dieser Requests überfordert sind. Ein Circuit Breaker schützt andere Services und verrichtet seine Arbeit während des Sendens von Daten (Abschicken von Requests an andere Services).

Mehr unter:

- <https://resilience4j.readme.io/docs/circuitbreaker>
- <https://www.vinsguru.com/circuit-breaker-pattern/>

#### Rate Limiter

Micro-Services sollten das _Rate Limiter_ Pattern implementieren. Damit wird sichergestellt, dass die eigenen Endpunkte nicht zu oft pro Zeiteinheit angefragt werden. Die Limitierung der eingehenden Requests pro konfigurierbaren Zeitintervall schützt den eigenen Service vor Überlastung. Der Rate Limiter verrichtet seine Arbeit während des Empfangs von Daten (Empfang von Requests von anderen Services).

Mehr unter:

- <https://resilience4j.readme.io/docs/ratelimiter>
- <https://www.vinsguru.com/rate-limiter-pattern/>

### Actuator / Prometheus / Grafana

Mithilfe der "actuator" Dependency wird eine Spring Boot Anwendung um sogenannte _Actuator_ Endpunkte "angereichert". Sie ermöglichen u.a. die Überwachung der Anwendung. Spring Boot enthält eine Reihe integrierter Endpunkte und erlaubt, eigene hinzuzufügen. So liefert z.B. der "Health" Endpunkt grundlegende Informationen zum Zustand der Anwendung.

Sämtliche Metriken können in einem speziellen Format zur Verfügung gestellt werden, sodass ein separat laufender _Prometheus_ server die Metriken über ihre Endpunkte "scrapen" kann. Prometheus dient der Überwachung einer oder mehrerer Anwendungen.

_Grafana_ wiederum kann die Monitoring-Daten bei Prometheus anfragen und diese in individuell gestalteten Boards grafisch ansprechend darstellen.

Mehr unter:

- <https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html>
- <https://spring.io/guides/gs/actuator-service/>
- <https://www.baeldung.com/spring-boot-actuators>
- <https://prometheus.io/>
- <https://grafana.com/>

### GitHub Actions

_GitHub Actions_ ist eine CI/CD Plattform mittels der komplette Build-Pipelines automatisiert werden können. Durch sogenannte _Workflows_, welche Event-getriggert starten, lassen sich verschiedene Prozesse, wie z.B. Build, Test und Deploy, abbilden. Die Workflows werden in YAML Dateien konfiguriert und sind Teil des Projekts. Bei der REWE digital löst GitHub Actions mittelfristig _Jenkins_ ab.

Mehr unter:

- <https://github.com/features/actions>

### Jib

Mittels _Jib_ kann ein optimiertes Docker-Image einer Java-Anwendung erstellt werden. Wir verwenden Jib als Gradle-Plugin.

Mehr unter:

- <https://github.com/GoogleContainerTools/jib>

## Hands-on Spring Boot Service "My User Management"

### Start / Tests / App Config

Der "my-user-mgmt" Service benötigt eine _PostgreSQL_ Datenbank. Diese startest du lokal mittels _docker-compose_:

```bash
docker-compose up
```

Nun kannst du den Service starten:

```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

Neben der allgemeinen Konfiguration der Spring Boot Anwendung für den Produktionsbetrieb (in `application.yml`) gibt es eine weitere Konfiguration für den lokalen Betrieb auf dem Entwicklerrechner (in `application-local.yml`). Diese lokale Config setzt alle nötigen Parameter für die lokale Datenbank. Darüber hinaus sind beliebig viele weitere Config Files denkbar, z.B. kann eine Konfiguration sinnvoll sein, die nur während der Ausführung der Tests greift. Die `application.yml` wird beim Start der App immer eingelesen.

Mit

```bash
./gradlew check
```

kannst du alle Tests ausführen.

### Endpoints

Der "my-user-mgmt" Service enthält einen Controller namens `UserController`, welcher zwei Endpunkte zur Verfügung stellt: `GET /api/users` und `POST /api/users`. Über diese beiden Endpunkte können vorhandene User zurück gegeben bzw. ein neuer User angelegt werden.

Da bisher keine User angelegt wurden, liefert der `getUsers` Endpunkt eine leere Liste zurück. Dies lässt sich am einfachsten überprüfen, indem man im Browser `http://localhost:8080/api/users` öffnet.

Um ein paar Test-Daten anzulegen (zu "seeden"), führen wir das `extra/db/dbseed.sh` shell script aus:

```bash
extra/db/dbseed.sh
```

Ein erneuter Aufruf von `http://localhost:8080/api/users` liefert nun die zuvor angelegten User als _JSON_ Objekt zurück.

### Swagger

Unter `http://localhost:8080/apidoc` findest du die automatisch erstellte Dokumentation aller Endpunkte. Gesteuert wird die Erstellung über entsprechende Java _Annotations_ im Controller.

### Metrics

Die im Prometheus-Format vorliegenden Metriken können über ihre jeweiligen Endpunkte unterhalb von `http://localhost:8080/admin` erreicht werden. So ist z.B. der "Health" Endpunkt unter `http://localhost:8080/admin/health` erreichbar. Er gibt unter anderem Auskunft darüber, ob die Anwendung läuft sowie antwortet und genug Speicherplatz vorhanden ist, etc.
