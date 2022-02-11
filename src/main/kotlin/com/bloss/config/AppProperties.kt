package com.bloss.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties(
    @Value("\${app.tokenSecret}") private val tokenSecret: String,
    @Value("\${app.tokenExpirationMSec}") private val tokenExpirationMSec: Long,
    @Value("\${app.tokenPrefix}") private val tokenPrefix: String,
    @Value("\${app.headerString}") private val headerString: String
) {
    val auth = Auth(tokenSecret, tokenExpirationMSec, tokenPrefix, headerString)

    class Auth(
        val tokenSecret: String,
        val tokenExpirationMSec: Long,
        val tokenPrefix: String,
        val headerString: String
    )
}
