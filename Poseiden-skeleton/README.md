![Java](https://img.shields.io/badge/made%20with-JAVA-%23C9284D?style=for-the-badge&logo=java&logoColor=#EC1C24)
![image](https://camo.githubusercontent.com/49fbb99f92674cc6825349b154b65aaf4064aec465d61e8e1f9fb99da3d922a1/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f68746d6c352d2532334533344632362e7376673f7374796c653d666f722d7468652d6261646765266c6f676f3d68746d6c35266c6f676f436f6c6f723d7768697465)


____________________

# Poseidon Application
> -- _Poseidon Inc._ --

## Technical:

- Java 14.0.1
- Maven 3.8.4
- Thymeleaf
- MySQL

## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Credentials

User already in database

```bash
login: admin  password: Admin123!
login: user   password: User123!
login: user2  password: User123!
```

## Build
Build application with the command `mvn clean install` for the back-end side.

For the front-side, install NodeJS and NPM from https://nodejs.org.
Run the command `npm install`.

## Run
To run the application you have to update the `application.properties` in the `resources` folder:
```properties
################### DataSource Configuration ##########################

spring.datasource.url=jdbc:mysql://localhost:3306/demo?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sqlpassword!

################### Hibernate Configuration ##########################

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=create-drop
#spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

################### App Properties ##########################
poseidon.app.jwtSecret=poseidonSecretKey
poseidon.app.jwtExpirationMs=86400000
```
Change the `spring.datasource.username=your_username` and the `spring.datasource.password=your_password`.

For the back-end side run the command `mvn spring-boot:run` then navigate to the Angular folder and run the command `npm run start` this will compile the Angular app and automatically launch it in the browser on the URL http://localhost:4200.


## Try the application

You can run the [Data Script](src/main/resources/MySQL/DataTest.sql) to have some fake users so you can test all the features.

### Super user
You can use user with email '_admin@paymybuddy.com_' and password
'_admin123_' to connect as a super user with 'ADMIN' authorizations.

Otherwise you just have to register as a new user.

## Technology Stack
Component         | Technology
---               | ---
Frontend          | [Angular 9](https://github.com/angular/angular)
Backend (REST)    | [SpringBoot 2.5.6](https://projects.spring.io/spring-boot) (Java)
Database          | [MySQL](https://www.mysql.com/)
Security          | Spring Security & [JSON Web Token](https://jwt.io/)
Persistence       | JPA (Using Spring Data)
Client Build Tools| [angular-cli](https://github.com/angular/angular-cli) npm
Server Build Tools| Maven(Java)

## Documentation

### SQL Scripts
Contains the principal database and the database for the tests.
https://github.com/simoncourtecuisse/PayMyBuddy/tree/master/src/main/resources/MySQL

### Model Database (SQL)

![DatabaseModel_PMB](src/main/resources/Docs/DatabaseModel_PMB.png)

### UML Diagram

![UML_PMB](src/main/resources/Docs/UML_PMB.png)

### Other Resources
See [HELP.md](https://github.com/simoncourtecuisse/PayMyBuddy/blob/main/PayMyBuddy/HELP.md) for helpful documentation regarding Spring Boot and [README.md](/README.md) for important commands and documentation relating to Angular and its embedded server.
