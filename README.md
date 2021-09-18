# Triple_assignment

트리플 과제2 도시 조회 API based on Spring Boot, JPA Hibernate ORM with MySQL, QueryDSL, Spring REST DOCS

## Requirements & Environment
1. Java - OpenJDK 11.x.x
2. Spring - 2.4.x
3. Gradle - 6.9.x
4. MySQL - 8.0.xx
5. ORM - JPA Hibernate
6. OS - MacOS 10.15.x
7. IDE - IntelliJ IDEA

## Installations

**1. Repository 클론**
```bash
git clone https://github.com/inchori/Triple_assignment
```

**2. MySQL DB 생성**
```bash
create database city_travel
```

**3. Database username과 password 설정**
+ `src/main/resources/application.yml` 파일 생성
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/city_travel?useSSL=false&serverTimezone=Asia/Seoul
    username: 
    password:

  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        show_sql: true
        format_sql: true

```
+ `spring.datasource.username` and `spring.datasource.password` 본인의 MySQL 설정에 맞게 변경

**4. 빌드 & 실행**
```bash
./gradlew build
cd build/libs
java -jar assignment-0.0.1-SNAPSHOT.jar
```

## REST API DOCS 보기
REST API 문서를 보고 싶으면 http://localhost:8080/docs/index.html 에서 확인할 수 있습니다.
Postman이나 다른 클라이언트를 통해 테스트해볼 수 있습니다.
