package com.bloss.mainservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties(
    @Value("\${app.security.tokenSecret}") private val tokenSecret: String,
    @Value("\${app.security.tokenExpirationMSec}") private val tokenExpirationMSec: Long,
    @Value("\${app.security.tokenPrefix}") private val tokenPrefix: String,
    @Value("\${app.security.headerString}") private val headerString: String
) {
    val auth = Auth(
        tokenSecret,
        tokenExpirationMSec,
        tokenPrefix,
        headerString
    )

    class Auth(
        val tokenSecret: String,
        val tokenExpirationMSec: Long,
        val tokenPrefix: String,
        val headerString: String
    )
}
