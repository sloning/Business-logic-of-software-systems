package com.bloss.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties(
    @Value("\${app.security.tokenSecret}") private val tokenSecret: String,
    @Value("\${app.security.tokenExpirationMSec}") private val tokenExpirationMSec: Long,
    @Value("\${app.security.tokenPrefix}") private val tokenPrefix: String,
    @Value("\${app.security.headerString}") private val headerString: String,
    @Value("\${app.schedule.scheduleToHidePosts}") private val scheduleToHidePosts: String,
    @Value("\${app.schedule.millisecondsToHideByCreationDate}") private val millisecondsToHideByCreationDate: Long,
    @Value("\${app.schedule.millisecondsToHideByLastWatchedDate}") private val millisecondsToHideByLastWatchedDate: Long
) {
    val auth = Auth(
        tokenSecret,
        tokenExpirationMSec,
        tokenPrefix,
        headerString
    )
    val schedule = Schedule(
        scheduleToHidePosts,
        millisecondsToHideByCreationDate,
        millisecondsToHideByLastWatchedDate
    )

    class Auth(
        val tokenSecret: String,
        val tokenExpirationMSec: Long,
        val tokenPrefix: String,
        val headerString: String
    )

    class Schedule(
        val scheduleToHidePosts: String,
        val millisecondsToHideByCreationDate: Long,
        val millisecondsToHideByLastWatchedDate: Long
    )
}
