# Budget Tracking App

## Running the Application

### Prerequisites

- **Java 17** or higher
- **MySQL Database**
- **FlyWay** migration tools

### Setup

Update src/main/resources/application.properties with your database and Flyway configuration.

spring.datasource.url=jdbc:mysql://localhost:3306/your_database

spring.datasource.username=your_username

spring.datasource.password=your_password

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=none

spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl




spring.flyway.url=jdbc:mysql://localhost/your_database

spring.flyway.password=your_password

spring.flyway.user=your_username

spring.flyway.locations=classpath:db/migration


Ensure you have Flyway installed and configured. If using Flyway CLI, run the following command to apply migrations:

flyway migrate
