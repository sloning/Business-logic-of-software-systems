package com.bloss.userinfoservice

import com.bloss.userinfoservice.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class UserInfoServiceApplication

fun main(args: Array<String>) {
    runApplication<UserInfoServiceApplication>(*args)
}
