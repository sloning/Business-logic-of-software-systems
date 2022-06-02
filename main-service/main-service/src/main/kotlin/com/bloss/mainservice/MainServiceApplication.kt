package com.bloss.mainservice

import com.bloss.mainservice.config.AppProperties
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableProcessApplication
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class MainServiceApplication

fun main(args: Array<String>) {
    runApplication<MainServiceApplication>(*args)
}
