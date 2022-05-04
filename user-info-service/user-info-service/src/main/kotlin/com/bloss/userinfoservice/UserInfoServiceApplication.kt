package com.bloss.userinfoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@SpringBootApplication
class UserInfoServiceApplication

fun main(args: Array<String>) {
    runApplication<UserInfoServiceApplication>(*args)
}
