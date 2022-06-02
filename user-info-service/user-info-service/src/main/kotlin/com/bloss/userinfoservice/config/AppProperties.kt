package com.bloss.userinfoservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties(
    @Value("\${app.rmq.host}") private val rmqHost: String,
    @Value("\${app.rmq.port}") private val rmqPort: Int,
    @Value("\${app.rmq.username}") private val rmqUsername: String,
    @Value("\${app.rmq.password}") private val rmqPassword: String
) {
    val rmq = Rmq(
        rmqHost,
        rmqPort,
        rmqUsername,
        rmqPassword
    )

    class Rmq(
        val rmqHost: String,
        val rmqPort: Int,
        val rmqUsername: String,
        val rmqPassword: String
    )
}
