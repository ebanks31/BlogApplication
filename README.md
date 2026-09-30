# Blog Application

<<<<<<< HEAD
A full-stack blog application with a Spring Boot REST API and an Angular web client. Users can browse blogs, create and edit blog content, manage accounts and users, and work with blog posts and comments.
=======
This is a blog application that uses Java 25, Spring boot 4.1.1, and Angular 8.
>>>>>>> 3d5180bd4b280cb5c25311af9d966d1adf54aeac

## Projects

| Project | Location | Purpose | Default URL |
| --- | --- | --- | --- |
| Spring Boot backend | [`BlogApplicationSpringBoot`](BlogApplicationSpringBoot) | REST API, validation, services, persistence, caching, and integrations | `http://localhost:9600` |
| Angular frontend | [`BlogApplicationAngular`](BlogApplicationAngular) | Browser UI, navigation, forms, blog and account pages | `http://localhost:4200` |

Read the project-specific documentation for deeper details:

- [Spring Boot backend README](BlogApplicationSpringBoot/README.md)
- [Angular frontend README](BlogApplicationAngular/README.md)

## Architecture

```text
Browser
	|
	| HTTP requests to localhost:9600
	v
Angular 22 frontend :4200
	|
	v
Spring Boot REST controllers :9600
	|
	v
Validators -> Services -> Spring Data repositories
															|
															v
												 MySQL database

Optional backend integrations:
Redis/Hazelcast, SMTP, Kafka-compatible publishing, Elasticsearch, Actuator, Prometheus
```

The backend follows a layered structure:

1. REST controllers map HTTP requests and responses.
2. Validators check incoming domain objects and identifiers.
3. Services contain business operations.
4. Repositories provide persistence through Spring Data JPA.
5. Models represent users, accounts, blogs, blog posts, and comments.

The frontend uses Angular components, routing, forms, and injectable services. Frontend services currently call the backend at `http://localhost:9600`; this is a development configuration and should be moved to Angular environment configuration for deployment.

## Prerequisites

Install the following before running both projects:

- Git
- Node.js `22.22.3` through `22.x`, `24.15.0` through `24.x`, or `26.0.0+`
- npm 8 or later
- JDK 25 for the backend
- MySQL for normal backend profiles
- Google Chrome to run the Angular Karma tests

Optional backend services include Redis or Hazelcast, SMTP, Elasticsearch, and the configured Kafka-compatible HTTP endpoint.

## Quick Start

### 1. Create the database

The repository includes [`BlogApplication.sql`](BlogApplication.sql). It creates the `blogapplication` database, tables, relationships, and sample data.

The default backend configuration expects:

```text
Database: blogapplication
Host: localhost:3306
User: root
Password: admin
```

Change these values before using the application outside local development. The script contains destructive `DROP DATABASE` and `DROP TABLE` statements, so review it before running it against an existing database.

### 2. Start the backend

Open a terminal in `BlogApplicationSpringBoot`:

```powershell
cd E:\BlogApplication\BlogApplicationSpringBoot
.\mvnw.cmd spring-boot:run
```

The API starts at `http://localhost:9600`.

To use the local profile:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

### 3. Start the frontend

Open a second terminal in `BlogApplicationAngular`:

```powershell
cd E:\BlogApplication\BlogApplicationAngular
npm install
npm start
```

Open `http://localhost:4200` in a browser. Start the backend first when using pages that load or submit API data.

## Backend

The backend is a Spring Boot application configured in [`BlogApplicationSpringBoot/pom.xml`](BlogApplicationSpringBoot/pom.xml). Its current build configuration targets Java 25 and uses a Spring Boot `4.1.0-M2` parent.

### Main API resources

| Resource | Example endpoints |
| --- | --- |
| Blogs | `GET /blogs`, `POST /blogs/blog/add`, `PUT /blogs/blog/edit/{blogId}` |
| Blog posts | `GET /blogs/blog/{blogId}/posts`, `POST /blogs/blog/{blogId}/posts/post/add` |
| Comments | `GET /blogs/blog/{blogId}/posts/{blogPostId}/comments`, `POST .../comments/add` |
| Users | `GET /users`, `POST /users/user/add`, `PUT /users/user/edit/{userId}` |
| Accounts | `GET /accounts`, `POST /accounts/add`, `PUT /accounts/edit/{accountId}` |

Most update and delete routes are documented in the [backend README](BlogApplicationSpringBoot/README.md). Actuator endpoints are also configured under `/actuator`.

### Backend configuration

Configuration files are in [`BlogApplicationSpringBoot/src/main/resources`](BlogApplicationSpringBoot/src/main/resources):

- `application.properties` for default settings
- `application-dev.properties` for development
- `application-local.properties` for local development
- `application-stage.properties` for staging
- `application-prod.properties` for production-style settings
- `src/test/resources/application-test.properties` for H2 tests

Important operational notes:

- The base configuration uses `spring.jpa.hibernate.ddl-auto=create`; do not use it against valuable data.
- Several property files contain example database or SMTP credentials. Use environment variables or a secrets manager.
- Spring Security is not enabled in the current POM, so the API should be treated as unauthenticated.
- Actuator exposure is broad in the current configuration and should be restricted before deployment.
- Controllers allow CORS from `http://localhost:4200`.

### Backend build and tests

From `BlogApplicationSpringBoot`:

```powershell
.\mvnw.cmd test
.\mvnw.cmd clean package
```

The Maven configuration includes unit, integration-style, and coverage-related test setup. The repository also contains focused controller and service tests under `src/test/java`.

## Frontend

The frontend is an Angular application configured in [`BlogApplicationAngular/package.json`](BlogApplicationAngular/package.json) and [`BlogApplicationAngular/angular.json`](BlogApplicationAngular/angular.json).

### Technology

- Angular `22.2.x` and Angular CLI `22.2.x`
- TypeScript `6.0.x`
- RxJS `7.8.x`
- Bootstrap 4 and ngx-bootstrap
- CKEditor 4 Angular integration
- Jasmine and Karma for unit tests

### Frontend routes

| Route | Purpose |
| --- | --- |
| `/blogs` | Blog list |
| `/blogs/addBlog` | Create a blog |
| `/blogs/blog/:id` | Blog details and post list |
| `/blogs/blog/:blogId/posts/:blogPostId` | Blog post details and editing |
| `/blogs/blog/:blogId/posts/post/add` | Create a blog post |
| `/accounts` | Account list |
| `/accounts/:id` | Account details |
| `/about` | About page |
| `/contact` | Contact page |

### Frontend commands

From `BlogApplicationAngular`:

```powershell
npm install
npm start
npm run build
npm run build -- --configuration production
npm test
npm test -- --no-watch
```

Generated documentation commands are also available:

```powershell
npm run generate-docs
npm run serve-docs
```

The frontend currently loads CKEditor 4 from a CDN and relies on network access for that script.

## Diagrams and Documentation

Backend architecture sources are available in:

- [Backend request flowchart](BlogApplicationSpringBoot/architecture-flowchart.md)
- [Backend high-level architecture](BlogApplicationSpringBoot/architecture-high-level.md)
- [Backend domain class diagram](BlogApplicationSpringBoot/class-diagram.puml)
- [Backend layered architecture UML](BlogApplicationSpringBoot/architecture.puml)

Additional project documentation is stored in [`Documents`](Documents):

- Requirements in DOCX and XLSX formats
- Backend unit-test scenarios
- UML diagram image
- Generated JavaDocs
- Generated Angular component documentation

Generated documentation also exists within the projects under `BlogApplicationSpringBoot/JavaDocs` and `BlogApplicationAngular/documentation`.

## Repository Layout

```text
BlogApplication/
|-- BlogApplicationSpringBoot/  Spring Boot REST API
|-- BlogApplicationAngular/    Angular web client
|-- Documents/                  Requirements and generated documentation
|-- BlogApplication.sql         MySQL schema and sample data
|-- .circleci/                  CI configuration
|-- BlogReact/                  Separate React project area
`-- README.md                   This full-stack overview
```

## Known Limitations

- The backend and frontend are maintained as separate applications and must be started independently.
- Frontend API URLs are currently hard-coded to localhost.
- Backend authentication and authorization are not enabled.
- Local configuration contains example credentials that must not be reused in production.
- The SQL setup script is destructive and should be treated as a development fixture.
- Optional integrations such as Redis, Hazelcast, SMTP, Elasticsearch, and Kafka are not bundled with the repository.

## License

No license file is currently present in the repository. Add an appropriate license before distributing the project.
