package com.bloss.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder.getContext
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            val token: String = jwtTokenProvider.resolveToken(request as HttpServletRequest)

            if (jwtTokenProvider.verifyToken(token)) {
                val authentication: Authentication = jwtTokenProvider.getAuthentication(token)
                getContext().authentication = authentication
            }
            chain.doFilter(request, response)
        } catch (e: AuthenticationException) {
            val resp: HttpServletResponse = response as HttpServletResponse
            resp.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Чтобы просмотреть этот контент, вы должны быть аутентифицированы"
            )
        }
    }
}