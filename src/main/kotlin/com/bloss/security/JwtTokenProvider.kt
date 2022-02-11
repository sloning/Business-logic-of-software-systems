package com.bloss.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.JWTVerifier
import com.bloss.config.AppProperties
import com.bloss.exception.JwtAuthenticationException
import com.bloss.exception.JwtResolvationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(private val appProperties: AppProperties) {
    private val algorithm: Algorithm = Algorithm.HMAC256(appProperties.auth.tokenSecret)

    fun createToken(subject: String): String {
        val expirationDate = Date(Date().time + appProperties.auth.tokenExpirationMSec)

        return JWT.create()
            .withSubject(subject)
            .withIssuer("bloss")
            .withExpiresAt(expirationDate)
            .sign(algorithm)
    }

    fun resolveToken(req: HttpServletRequest): String {
        val bearerToken = req.getHeader(appProperties.auth.headerString)
        return if (bearerToken != null && bearerToken.startsWith(appProperties.auth.tokenPrefix)) {
            bearerToken.substring(7)
        } else throw JwtResolvationException("Неверный JWT токен. Пожалуйста, пройдите аутентификацию вновь")
    }

    fun verifyToken(token: String): Boolean {
        return try {
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer("bloss")
                .build()
            verifier.verify(token)
            true
        } catch (e: JWTVerificationException) {
            throw JwtAuthenticationException("Ваша сессия устарела. Пожалуйста, пройдите аутентификацию вновь")
        }
    }

    fun getPlayerId(token: String): String {
        return JWT.decode(token).subject
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: JwtUser = JwtUser.create(getPlayerId(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }
}
