# Ever-changing Hierarchy GmbH

The project would give a help for company to build their employee hierarchy, and the ability for an employee to know who is their bosses.

## Getting Started

Technology stack includes [Spring Boot](https://spring.io/projects/spring-boot) + Java + Gradle

### Prerequisites
- [Java](https://www.oracle.com/technetworkk/java/javase/downloads/jdk8-downloads-2133151.html) >= 8
- [Mysql](https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/) 8.0


### Usage
1. Start Mysql server locally -> create user -> create database
2. Go to `/src/main/resources/application-dev.yml` and update corresponding database config 
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/backend_assignment
    username: mysql-user
    password: mysql-user-password
```
3. Start dev server
```bash
$ cd <project-location>
$ ./gradlew bootRun
```
From now, we should able to access web api via `http://localhost:8080`

## Running the tests
Test cases cover most of core logic, which is locate in `service layer` and `repository layer`
```bash
# run all tests
$ ./gradlew test
# run a specific test
$ ./gradlew test --tests=<testname>
# much more information
$ ./gradlew test --tests=<testname> --info
```
## Coding style
Follow default Kotlin code style on IntelliJ IDE

## Available APIs
```
POST /api/v1/hierarchy (create hierarchy)
GET  /api/v1/hierarchy (get hierarchy)
GET  /api/v1/staffs/{name}/superiors (get superiors)
POST /api/v1/users/seed (seed test user for authentication)
POST /api/v1/auth (login)
```
## A deeper look

### Applied design pattern
- Inverse of control
- Service pattern
- Repository pattern
- Facade pattern
- SOLID
- DTO - Data transfer object
- MVC

### Build hierarchy algorithm
- Step 1: Use adjacent matrix to represent relationship between employee
- Step 2: Look on matrix to find out the tree root
- Step 3: Build comprehensive tree from root to leafs

**For example:** the company has following employees relationship
```json
{
	"Pete": "Nick",
	"Barbara": "Nick",
	"Nick": "Sophie",
	"Sophie": "Jonas"
}
```
For each relationship `subordinate -> superior`, we find out its position in matrix, increase by 1 to express an employee is the boss of another one, hence increase 2 if someone is indirect boss of someone. We end up with following matrix

|         | Barbara | Jonas | Sophie | Nick | Pete |
|---------|---------|-------|--------|------|------|
| Barbara | 0       | 2     | 2      | 1    | 0    |
| Jonas   | 0       | 0     | 0      | 0    | 0    |
| Sophie  | 0       | 1     | 0      | 0    | 0    |
| Nick    | 0       | 2     | 1      | 0    | 0    |
| Pete    | 0       | 2     | 2      | 1    | 0    |

**0:** means two employees has no relationship

**1:** means one is boss of another one

**2:** means one is indirectly boss of another one

=> **Root** is the one who is direct boss or indirect boss of everyone

In case of we found > 1 root or zero root, the tree is conflict which lead to error.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
