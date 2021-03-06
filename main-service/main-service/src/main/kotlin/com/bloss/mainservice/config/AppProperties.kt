package com.bloss.mainservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties(
    @Value("\${app.security.tokenSecret}") private val tokenSecret: String,
    @Value("\${app.security.tokenExpirationMSec}") private val tokenExpirationMSec: Long,
    @Value("\${app.security.tokenPrefix}") private val tokenPrefix: String,
    @Value("\${app.security.headerString}") private val headerString: String,
    @Value("\${app.rmq.host}") private val rmqHost: String,
    @Value("\${app.rmq.port}") private val rmqPort: Int,
    @Value("\${app.rmq.username}") private val rmqUsername: String,
    @Value("\${app.rmq.password}") private val rmqPassword: String
) {
    val auth = Auth(
        tokenSecret,
        tokenExpirationMSec,
        tokenPrefix,
        headerString
    )

    val rmq = Rmq(
        rmqHost,
        rmqPort,
        rmqUsername,
        rmqPassword
    )

    class Auth(
        val tokenSecret: String,
        val tokenExpirationMSec: Long,
        val tokenPrefix: String,
        val headerString: String
    )

    class Rmq(
        val rmqHost: String,
        val rmqPort: Int,
        val rmqUsername: String,
        val rmqPassword: String
    )
}
