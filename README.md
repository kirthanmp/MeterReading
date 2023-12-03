# MeterReading
ETPA - Meter Reading

## Installation

Clone the repository

https://github.com/kirthanmp/MeterReading.git

git clone https://github.com/kirthanmp/MeterReading.git
or Download the zip file

## Compilation

If you use IDE like Intellij you can use maven tab to compile and run the project.
clean-install command
mvn clean install

to run
mvn spring-boot:run

## Swagger UI
http://localhost:8080/swagger-ui/index.html

## H2 Database
http://localhost:8080/h2-console/

JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password: 

Press connect

## Postman
https://www.postman.com/mpkirthan/workspace/meterreading/collection/6568245-d4e17ea6-acac-4f8a-b4c9-1b93d62ae884?action=share&creator=6568245

## Usage
Example sequence of calls

Sequence of calls

1. Create profile fraction.
2. Create meter reading.
3. You can update either using update profile creation or update meter reading api.
4. Delete them accordingly using delete profile fraction and delete meter reading api.
5. Consumption per month api to get consumption per meterid and month / check monthly consumption for billing purposes.
6. Get profile fraction by id.
7. Get meter reading by id.
8. Get profile fraction events.
9. Get meter reading events.
10. Get profile.
11. Get profile details by profile name.





