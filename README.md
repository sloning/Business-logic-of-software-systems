# Лабораторная работа по предмету "Бизнес-логика программных систем"

Приложение представляет из себя аналог Циана. Реазлизованы следющие бизнес-процессы:

* Аутентификация и авторизация
* Создание новых объявлений о продаже/сдаче в аренду недвижимости.
* Модерирование объявлений

Использованные технологии:

* Kotlin
* Spring Boot
* Spring Security
* Sprint Data Jpa
* Atomikos
* Apache Kafka
* OpenAPI
* PostgreSQL
* Gradle
* Docker

## BPMN

TODO

## Сборка и запуск

Необходимо указать следующие системные переменные:
db_host, db_username, db_password, SECURITY_SECRET, email_username, email_password.

Сборка и запуск приложения:

```
./gradlew bootRun
```

## Сбора и запуск с использованием Docker

```
./gradlew bootJar
docker build -t bloss:latest .
docker-compose up -d
```
