<b> Blog Application</b>

This is a blog application that uses Java 8, Spring boot, and Angular 8.

This application allows users to create a blog, add blog posts, and add comments to blog posts.

<b>Backend</b>

The backend consists of a Spring Boot Application that contains the REST endpoints and repositories.
The libraries that are used are Spring Boot Spring Data, JPA/Hibernate, Spring Actuator, and Spring AOP.

The layers of the Spring Boot application are below:
1.) Controller layer
2.) Validation layer
3.) DTO layer
4.) Service layer

I am looking to include the Spring Security library at a later time.

<b>Frontend</b>

The front end consists of Angular 8, CSS, and Bootstrap.
The text editor I am using is https://ckeditor.com/. This is a great editor that contains a formatting toolbar that is compatible with Angular 7.

This application isn't finished just yet but this project is used to help me better understand the AngularJS framework.

The database I used was MYSQL. I have attached database scripts that I used under the BlogApplication.sql file.

<b>Documents</b>

I have added a <b>Documents</b> folder that will contain various documents for this project.

Documents include

1. Requirements. (.docx,.xlsx)
2. Unit tests scenarios.
3. UML Diagram
4. JavaDocs (Java)
5. CompoDocs (Angular 8)

I will add additional documents as I worked through this project including user stories and integration test scenarios (i.e. testing multiple facets of the application including backend and front end parts of the application).

<b>TODO</b>
For the backend, I need to add the functionality to send API statistics to ELK (Elastic Search, Logstash, Kibana) via Kafka.
API statistics can include the response time, request counts, etc.
