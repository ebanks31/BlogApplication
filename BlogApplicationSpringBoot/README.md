# Blog Application

A Spring Boot REST API for managing blogs, blog posts, comments, users, and accounts. The application uses Spring Data JPA for persistence, exposes operational endpoints through Actuator, and includes integrations for caching, Redis, Hazelcast, email, Kafka-style event publishing, Prometheus metrics, and optional Elasticsearch configuration.

> **Project status:** This README documents the repository as it exists today. Some dependencies and configuration files are legacy or experimental. Review the security and deployment notes before running this application outside a local development environment.

## Contents

- [Features](#features)
- [Quick start](#quick-start)
- [Configuration](#configuration)
- [Profiles](#profiles)
- [HTTP API](#http-api)
- [API examples](#api-examples)
- [Architecture](#architecture)
- [Diagrams](#diagrams)
- [Known limitations and security notes](#known-limitations-and-security-notes)
- [Development workflow](#development-workflow)

## Features

- CRUD operations for blogs, blog posts, comments, users, and accounts.
- Nested blog post and comment routes.
- Bean validation components for the main domain objects.
- Spring Data JPA repositories backed by MySQL in the normal profiles.
- H2 configuration for tests.
- Spring Cache support enabled by the application entry point.
- Redis and Hazelcast dependencies available for caching/distributed use cases.
- Actuator endpoints and Micrometer Prometheus support.
- Swagger/Springfox annotations and dependencies for API documentation.
- CORS configured for the Angular development origin `http://localhost:4200`.
- Optional publishing of blog data to the configured Kafka HTTP endpoint.
- Email and SMTP configuration support in the local profile.
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"

## Technology stack

| Area | Repository configuration |
| --- | --- |
| Language | Java 25 source and target level |
| Build | Maven Wrapper (`mvnw`, `mvnw.cmd`) |
| Framework | Spring Boot `4.1.0-M2` parent |
| Persistence | Spring Data JPA, Hibernate, MySQL Connector/J |
| Test database | H2 in-memory database |
| Web | Spring MVC and Spring Data REST |
| API documentation | Springfox Swagger 3.0.0 dependencies |
| Caching | Spring Cache, Hazelcast, Redis/Jedis dependencies |
| Metrics | Spring Boot Actuator and Micrometer Prometheus registry |
| Utilities | Lombok, Gson, JSON Simple, Apache Commons Collections |

The exact dependency set and plugin versions are defined in [`pom.xml`](pom.xml).

## Repository layout

```text
.
--- pom.xml
--- mvnw / mvnw.cmd             Maven Wrapper launchers
--- Dockerfile                  Root container definition
--- src/main/java/com/blog/application
-   --- controllers/            REST controllers
-   --- model/                  JPA/domain models
-   --- repositories/           Spring Data repositories
-   --- service/                Service interfaces and implementations
-   --- validator/              Request/domain validation
-   --- config/                 Spring and Swagger configuration
-   --- aspects/                Cross-cutting logging/time tracking
-   --- cache/                  Cache-related services
-   --- exception/              Application exceptions and handlers
-   --- filter/                 Servlet filters
-   --- interceptors/           Request interceptors
--- src/main/resources
-   --- application.properties
-   --- application-*.properties Profile-specific configuration
-   --- logback.xml
-   --- banner.txt
--- src/test/java/              Unit and integration-style tests
--- src/test/resources/         Test profile configuration
--- JavaDocs/                   Generated JavaDoc output
```

The application starts from [`BlogApplication.java`](src/main/java/com/blog/application/BlogApplication.java). It enables component scanning, JPA repositories under `com.blog.application.repositories`, and Spring caching.

## Prerequisites

Install the following before building:

- A JDK that supports Java 25. The Maven compiler plugin is configured with `<source>25</source>` and `<target>25</target>`.
- A MySQL server for the default, development, local, stage, and production-style profiles.
- Maven is not required when using the included wrapper.
- Optional services, depending on the features you use: Redis, Hazelcast, an Elasticsearch node, an SMTP server, and the configured Kafka publishing endpoint.

Create the database expected by the default configuration before starting with a MySQL-backed profile:

```sql
CREATE DATABASE blogapplication;
```

The default properties use the MySQL user `root` and password `admin`. Change these values before sharing the application or connecting it to a real database.

## Quick start

### Windows

```powershell
.mvnw.cmd clean spring-boot:run
```

### macOS/Linux

```bash
./mvnw clean spring-boot:run
```

The application listens on port `9600` by default:

```text
http://localhost:9600
```

To select a profile, pass it as a system property:

```powershell
.mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

or:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

You can also set `SPRING_PROFILES_ACTIVE` in the environment before starting the application.

## Configuration

Configuration is split between `application.properties` and the profile files under `src/main/resources`.

Important properties include:

| Property | Purpose | Current repository value |
| --- | --- | --- |
| `server.port` | HTTP port | `9600` |
| `spring.datasource.jdbc-url` | MySQL JDBC connection | `jdbc:mysql://localhost:3306/blogapplication?useSSL=false` |
| `spring.datasource.username` | Database user | `root` |
| `spring.datasource.password` | Database password | `admin` |
| `spring.jpa.hibernate.ddl-auto` | Hibernate schema behavior | `create` in the base file; `update` in most profiles |
| `spring.boot.kafka.address` | Blog publishing endpoint | `http://127.0.0.1:9020/kafka/publishJSON` |
| `spring.boot.kafka.send` | Enable blog publishing | `true` in the base file; `false` in local |
| `dirty.fix.enabled` | Enables the Swagger actuator workaround | `true` |
| `management.endpoints.web.exposure.include` | Actuator exposure | `*` |
| `spring.data.elasticsearch.cluster-nodes` | Elasticsearch node | `127.0.0.1:9300` in local/base configuration |
| `spring.mail.host` | SMTP server | `smtp.gmail.com` in local configuration |

Prefer environment variables or an externalized configuration source for credentials and service URLs. Spring Boot maps environment variables such as `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD` to the corresponding properties.

### Database schema behavior

The base `application.properties` file uses:

```properties
spring.jpa.hibernate.ddl-auto=create
```

This can recreate the schema when the application starts and should not be used against valuable data. The `dev`, `local`, `stage`, and `prod` files use `update`. For controlled deployments, replace automatic schema mutation with a migration tool and a deliberate schema strategy.

## Profiles

Available property files are:

| Profile | File | Notes |
| --- | --- | --- |
| Default | `application.properties` | MySQL, schema creation, Kafka publishing enabled, broad Actuator exposure |
| `dev` | `application-dev.properties` | MySQL and schema update |
| `local` | `application-local.properties` | MySQL, schema update, Kafka publishing disabled, Elasticsearch and SMTP settings |
| `stage` | `application-stage.properties` | MySQL and schema update |
| `prod` | `application-prod.properties` | MySQL and schema update |
| `test` | `src/test/resources/application-test.properties` | H2 in-memory database and H2 console settings |

Run with a profile using either of these forms:

```powershell
$env:SPRING_PROFILES_ACTIVE = "local"
.\mvnw.cmd spring-boot:run
```

```bash
SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run
```

The profile files currently contain example credentials and service addresses. Treat them as templates and override sensitive values in your environment.

## HTTP API

The API does not use a global `/api` prefix. All routes below are relative to `http://localhost:9600`.

### Blogs

| Method | Path | Purpose |
| --- | --- | --- |
| `GET` | `/blogs` | List blogs |
| `GET` | `/blogs/blog/{blogId}` | Get a blog by ID |
| `POST` | `/blogs/blog/add` | Add a blog |
| `PUT` | `/blogs/blog/edit/{blogId}` | Edit a blog |
| `DELETE` | `/blogs/blog/delete/{blogId}` | Delete a blog |
| `GET` | `/blog/{blogId}/{userId}` | Retrieve the blog list for the supplied route parameters |
| `GET` | `/` | List blogs through the controller's root mapping |
| `POST` | `/blogs/add` | Add a blog through the alternate route |
| `POST` | `/blogs/edit/{blogId}` | Edit a blog through the alternate route |

### Blog posts

| Method | Path | Purpose |
| --- | --- | --- |
| `GET` | `/blogs/blog/{blogId}/posts` | List posts for a blog |
| `GET` | `/blogs/blog/{blogId}/posts/{blogPostId}` | Get a post |
| `POST` | `/blogs/blog/{blogId}/posts/post/add` | Add a post |
| `PUT` | `/blogs/blog/{blogId}/posts/post/edit/{blogPostId}` | Edit a post |
| `DELETE` | `/blogs/blog/{blogId}/posts/post/delete/{blogPostId}` | Delete a post |

### Comments

| Method | Path | Purpose |
| --- | --- | --- |
| `GET` | `/blogs/blog/{blogId}/posts/{blogPostId}/comments` | List comments |
| `POST` | `/blogs/blog/{blogId}/posts/{blogPostId}/comments/add` | Add a comment |
| `PUT` | `/blogs/blog/{blogId}/posts/{blogPostId}/comments/edit/{commentId}` | Edit a comment |
| `DELETE` | `/blogs/blog/{blogId}/posts/{blogPostId}/comments/delete/{commentId}` | Delete a comment |

### Users

| Method | Path | Purpose |
| --- | --- | --- |
| `GET` | `/users` | List users |
| `GET` | `/users/user/{userId}` | Get a user |
| `POST` | `/users/user/add` | Add a user |
| `PUT` | `/users/user/edit/{userId}` | Edit a user |
| `DELETE` | `/users/user/delete/{userId}` | Delete a user |

### Accounts

| Method | Path | Purpose |
| --- | --- | --- |
| `GET` | `/accounts` | List accounts |
| `GET` | `/accounts/{accountId}` | Get an account |
| `POST` | `/accounts/add` | Add an account |
| `PUT` | `/accounts/edit/{accountId}` | Edit an account |
| `DELETE` | `/accounts/delete/{accountId}` | Delete an account |

Most mutating endpoints accept a JSON request body and return HTTP 200 with a plain-text success message. Validation failures are raised as `BlogException` instances and handled by the application's exception advice. Check the controller and model classes for the exact JSON fields expected by each resource.

All REST controllers currently declare CORS for `http://localhost:4200`. Update the annotations or centralize CORS configuration when deploying a frontend from another origin.

## API examples

List blogs:

```bash
curl http://localhost:9600/blogs
```

Create a blog using the fields defined by the `Blog` model:

```bash
curl -X POST http://localhost:9600/blogs/blog/add \
  -H "Content-Type: application/json" \
  -d '{"title":"First post","description":"Hello from the blog API"}'
```

Update a user:

```bash
curl -X PUT http://localhost:9600/users/user/edit/1 \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"name":"Updated user"}'
```

The sample bodies are illustrative. Use the fields and relationships required by the corresponding model and validator in this repository.

## Testing

The project contains controller unit tests, service tests, validator tests, and integration-style tests under `src/test/java`.

Run the default Maven test lifecycle:

```powershell
.\mvnw.cmd test
```

```bash
./mvnw test
```

The configured Surefire plugin includes classes matching `**/*UnitTests.java` and excludes classes matching `**/*IntegrationTests.java`. This means the default test command is intentionally focused on unit tests. Integration-style tests may require additional profile, database, or test-context setup before they can be run reliably.

Run the full verification and create the package:

```powershell
.\mvnw.cmd clean verify
```

A JaCoCo report is configured during packaging. When generated, inspect the report under `target/site/jacoco/`.

## Packaging and Docker

Build the executable JAR:

```powershell
.\mvnw.cmd clean package
```

Start the packaged application:

```powershell
java -jar target/BlogApplication-2.0.0.jar
```

The root `Dockerfile` copies a JAR matching `target/*.jar` into an image and supports `JAVA_OPTS`:

```bash
docker build -t blog-application .
docker run --rm -p 9600:9600 \
  -e JAVA_OPTS="-Dspring.profiles.active=local" \
  blog-application
```

Before using the Dockerfile, verify the image runtime is compatible with the current Maven build. The root Dockerfile uses `openjdk:8-jdk-alpine`, while the POM targets Java 25. Update the container base image to a Java 25-compatible image, or align the Maven compiler target with the runtime, before relying on this image for deployment.

## Observability

Actuator endpoints are broadly exposed by the current configuration through:

```text
http://localhost:9600/actuator
```

The application also configures:

- Console logging with a timestamped pattern.
- File logging to `${java.io.tmpdir}/application.log`.
- `DEBUG` logging for `com.blog.application`.
- Response compression for common text and JSON content types when responses are at least 1 KB.
- Micrometer core and Prometheus registry dependencies.

Do not expose every Actuator endpoint publicly. Restrict the exposed endpoint set and add authentication before deploying to an untrusted network.

## Architecture

The code follows a conventional layered Spring application structure:

```text
HTTP request
    -> REST controller
    -> validator
    -> service interface/implementation
    -> Spring Data repository
    -> database
```

Cross-cutting concerns are implemented around this flow through filters, interceptors, AOP logging/time tracking, exception handling, and cache services. Domain objects live in `model`, persistence interfaces live in `repositories`, and business operations are separated behind service interfaces.

The main controllers are:

- `BlogRestController`
- `BlogPostRestController`
- `CommentsRestController`
- `UserRestController`
- `AccountRestController`

## Diagrams

The repository includes editable architecture diagrams:

- [Request flowchart](architecture-flowchart.md) - Mermaid flowchart showing request validation, services, caching, persistence, exceptions, and integrations.
- [Architecture UML](architecture.puml) - PlantUML class/component view of controllers, validators, services, repositories, and external systems.
- [Domain class diagram](class-diagram.puml) - PlantUML view of the core model relationships and controller contracts.
- [High-level architecture](architecture-high-level.md) - Mermaid view of application boundaries and external dependencies.

The existing [`model.uml`](model.uml), [`model.umlcd`](model.umlcd), and [`new_diagram.uxf`](new_diagram.uxf) files are retained as UMLLab and Umlet-compatible project artifacts.

## Known limitations and security notes

- **Credentials are present in property files.** Replace the MySQL, SMTP, and other example credentials with environment variables or a secrets manager. Never commit production secrets.
- **No Spring Security starter is enabled.** The POM contains the security dependency as a comment, so the API should be treated as unauthenticated unless security is added elsewhere.
- **Actuator exposure is broad.** `management.endpoints.web.exposure.include=*` can reveal operational information and should be narrowed.
- **Schema creation is destructive.** The default profile uses `ddl-auto=create`; use a migration strategy for persistent environments.
- **CORS is development-specific.** Controllers permit only `http://localhost:4200`.
- **The Docker runtime is outdated relative to the compiler target.** Resolve the Java 8 versus Java 25 mismatch before deployment.
- **Legacy API documentation dependencies are present.** Springfox and several older supporting dependencies may require compatibility work with the configured Spring Boot milestone version.
- **External services are not bundled.** MySQL, Redis, Hazelcast, Elasticsearch, SMTP, and the configured Kafka endpoint must be provisioned separately when those code paths are used.
- **Passwords are modeled and returned by some account test/API paths.** Review the domain model, serialization, storage, and response contracts before exposing account data to clients.

## Development workflow

1. Create or select a local JDK 25 environment.
2. Create the `blogapplication` MySQL database, or use the test profile with H2.
3. Copy the relevant profile settings into environment variables or an external configuration source.
4. Start the application with `spring-boot:run`.
5. Exercise the API with curl, Swagger tooling, or the frontend configured for `http://localhost:9600`.
6. Run `mvnw.cmd test` before packaging.
7. Run `mvnw.cmd clean verify` to compile, test, package, and generate the configured coverage report.

Generated JavaDocs are available in the [`JavaDocs`](JavaDocs) directory. UML artifacts are stored at the repository root in `model.uml`, `model.umlcd`, and `new_diagram.uxf`.

## License

No license file is currently present in the repository. Add a license before distributing the project if one is required.
