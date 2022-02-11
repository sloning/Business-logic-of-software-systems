package com.bloss

import com.bloss.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class BusinessLogicOfSoftwareSystemsApplication

fun main(args: Array<String>) {
    runApplication<BusinessLogicOfSoftwareSystemsApplication>(*args)
}
