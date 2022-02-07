# Ramp-Up Spring Boot

**!!! Work in Progress !!!**

## Spring Boot

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

### Run

Nachdem du deine Spring Boot Anwendung heruntergeladen hast, kannst du das Zip-File auspacken und die Anwendung mittels

```bash
./gradlew bootRun
```

starten. Da die Applikation noch nie gebaut wurde, geht dem Start automatisch ein Build voran.

Im Browser unter <http://localhost:8080/> antwortet der noch nicht sonderlich sinnvolle Service. Erster Durchstich geschafft ... :-)

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

Mehr unter:

- <https://spockframework.org/>
- <https://groovy-lang.org/>
- <https://www.testcontainers.org/>

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

### Controller, Service, Repository

In Spring Boot stellt ein sogenannter _Controller_ (z.B. `UserController`) einen oder mehrere Endpunkte (z.B. `/api/users`) zur Verfügung. Business-Logik sollte nicht in Controllern definiert werden. Controller können einen oder mehrere sogenannte _Services_ aufrufen. Ein Service kapselt die eigentliche Business-Logik. Dies können z.B. auch Anfragen an eine Datenbank sein, welche wiederum nicht direkt im Service stattfinden, sondern über ein sogenanntes `Repository` delegiert werden. In einem Repository werden eine oder mehrere DB-Anfragen formuliert. In der Regel geschieht dies via _Spring Data JPA_ (siehe Abschnitt unten). Neben der Kommunikation mit einem Repository können Services auch weitere Services aufrufen.

Controller-, Service- und Repository-Instanzen werden _Spring Beans_ genannt. Neben diesen drei Ausprägungen gibt es noch weitere Bean-Typen.

Mehr unter:

- <https://www.baeldung.com/spring-bean>

### Dependency Injection / Inversion of Control

Das Spring Framework - und somit Spring Boot - unterstützt die sogenannte _Dependency Injection_. Mit ihrer Hilfe können _Spring Beans_ vom Spring Framework automatisch instantiiert und an Klassen, die eine Abhängigkeit zu ihnen haben, übergeben werden.

Mehr unter:

- <https://www.baeldung.com/spring-dependency-injection>
- <https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring>
- <https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-spring-beans-and-dependency-injection.html>

### Spring Data JPA

_Spring Data JPA_ erlaubt es, einfache DB-Queries statt mittels SQL über entsprechend benamte Methoden auszudrücken. Das folgende Repository (ein Spring Bean über das DB-Zugriffe stattfinden) stellt eine Methode zur Verfügung, welche alle User (Tabelle `myusers`, Spalten `emailAddress` und `lastname`) zurückgibt. Die normierten Methodennamen folgen in ihrem Aufbau spezifischen Regeln und Konventionen und lassen sich mit etwas Übung in die entsprechende SQL-Query übersetzen:

```java
@Entity(name = "myusers")
public interface UserRepository extends Repository<User, Long> {
    // select * from myusers where emailAddress='<value of emailAddress>' and lastname='<value of lastname>';
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

### Security

Die REST-Endpunkte aller Services sind in aller Regel nicht öffentlich. Sie sind mittels _OAuth2_ abgesichert. Das heißt, dass sie nur aufgerufen werden können, wenn ein gültiges Token (_JWT_) im `Authorization` Header des Requests mitgeschickt wird. In der REWE digital werden die Tokens von einem zentralen Authorization Server angefordert und sind nur kurze Zeit gültig. Der Authorization Server ist nur im VPN erreichbar und via Basic Auth abgesichert.

Es wird der _Authorization Code Flow_ verwendet.

Mehr unter:

- <https://auth0.com/docs/get-started/authentication-and-authorization-flow/authorization-code-flow>
- <https://jwt.io/>

## Spring Boot Demo service

Der "demo" Service enthält einen Controller namens `UserController`, welcher einen Endpunkt `/api/users` zur Verfügung stellt.

TODO: More to come here ... :)
