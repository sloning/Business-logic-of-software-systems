package com.bloss.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.JWTVerifier
import com.bloss.config.AppProperties
import com.bloss.exception.JwtAuthenticationException
import com.bloss.exception.JwtResolvationException
import com.bloss.model.Role
import com.bloss.repository.UserXmlRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(private val appProperties: AppProperties) {
    private val userRepository = UserXmlRepository
    private val algorithm: Algorithm = Algorithm.HMAC256(appProperties.auth.tokenSecret)

    fun createToken(id: Long, role: Role): String {
        val expirationDate = Date(Date().time + appProperties.auth.tokenExpirationMSec)
        val claims: Map<String, Any> = mapOf("id" to id, "role" to role.toString())

        return JWT.create()
            .withPayload(claims)
            .withIssuer("bloss")
            .withExpiresAt(expirationDate)
            .sign(algorithm)
    }

    fun resolveToken(req: HttpServletRequest): String {
        val bearerToken = req.getHeader(appProperties.auth.headerString)
        return if (bearerToken != null && bearerToken.startsWith(appProperties.auth.tokenPrefix)) {
            bearerToken.substring(7)
        } else badToken()
    }

    private fun badToken(): Nothing =
        throw throw JwtResolvationException("Неверный JWT токен. Пожалуйста, пройдите аутентификацию вновь")

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

    private fun getClaims(token: String): Map<String, Claim> = JWT.decode(token).claims

    private fun getUserId(claims: Map<String, Claim>): Long = claims["id"]?.asLong() ?: badToken()

//    private fun getUserRole(claims: Map<String, Claim>): Role = claims["role"]?.`as`(Role::class.java) ?: badToken()

    private fun getUserRole(userId: Long): Role = userRepository.findRoleById(userId) ?: badToken()

    fun getAuthentication(token: String): Authentication {
        val claims: Map<String, Claim> = getClaims(token)
        val userId = getUserId(claims)
        val userDetails: JwtUser = JwtUser.create(userId, getUserRole(userId))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }
}
