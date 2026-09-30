# Blog Application Request Flow

```mermaid
flowchart TD
    Client[Browser or API Client]
    Cors[CORS: localhost:4200]
    Controller[REST Controller]
    Validator[Domain Validator]
    Service[Service Layer]
    Cache{Cached blog data?}
    Repository[Spring Data Repository]
    Database[(MySQL Database)]
    Exception[BlogExceptionAdvice]
    Actuator[Actuator and Micrometer]
    Kafka[Kafka-compatible HTTP endpoint]
    Mail[SMTP Mail Provider]

    Client --> Cors
    Cors --> Controller
    Controller --> Validator
    Validator -->|Valid request| Service
    Validator -->|Invalid request| Exception
    Service --> Cache
    Cache -->|Hit| Service
    Cache -->|Miss| Repository
    Repository --> Database
    Database --> Repository
    Repository --> Service
    Service --> Controller
    Controller --> Client
    Controller -->|Optional blog publishing| Kafka
    Service -->|Email operations| Mail
    Client -.->|Operational checks| Actuator
    Controller -.->|Unhandled exception| Exception
    Exception --> Client
```

## Flow notes

1. A client sends an HTTP request to one of the REST controllers.
2. Controllers validate request data before calling the service layer.
3. Blog reads can use the cache; cache misses are delegated to Spring Data repositories.
4. Repositories read from or write to the configured database.
5. Valid responses return through the controller to the client.
6. Application exceptions are converted into HTTP responses by `BlogExceptionAdvice`.
7. Blog publishing, email, and operational metrics are optional integrations.
