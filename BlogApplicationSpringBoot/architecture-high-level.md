# Blog Application High-Level Architecture

```mermaid
flowchart LR
    Client[Web Browser or API Client]

    subgraph Application[Blog Application - Spring Boot]
        Web[REST Controllers]
        Validation[Validators]
        Services[Service Layer]
        Cache[Cache Services]
        Persistence[Spring Data Repositories]
        Errors[Global Exception Advice]
        Observability[Actuator and Micrometer]
    end

    Database[(MySQL)]
    Redis[(Redis or Hazelcast)]
    Messaging[Kafka-compatible HTTP Endpoint]
    Email[SMTP Provider]
    Search[(Optional Elasticsearch)]

    Client --> Web
    Web --> Validation
    Validation --> Services
    Validation -. invalid request .-> Errors
    Services --> Persistence
    Services --> Cache
    Persistence --> Database
    Cache --> Redis
    Services -. optional blog events .-> Messaging
    Services -. email notifications .-> Email
    Services -. optional search .-> Search
    Web -. unhandled exception .-> Errors
    Client -. health and metrics .-> Observability
```

## Boundaries

- **Client boundary:** Browser or API consumers call the REST endpoints on port `9600`.
- **Web boundary:** Controllers map HTTP requests for blogs, posts, comments, users, and accounts.
- **Application boundary:** Validators, services, caching, exception handling, and observability coordinate application behavior.
- **Persistence boundary:** Spring Data repositories access MySQL through JPA.
- **Integration boundary:** Redis/Hazelcast, the Kafka-compatible endpoint, SMTP, and Elasticsearch are optional external dependencies.
