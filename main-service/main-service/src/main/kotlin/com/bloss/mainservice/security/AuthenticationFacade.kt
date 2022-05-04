package com.bloss.mainservice.security

import com.bloss.mainservice.exception.WrongCredentialsException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade {
    val userId: Long
        get() {
            val auth: Authentication = SecurityContextHolder.getContext().authentication
            if (auth !is AnonymousAuthenticationToken) {
                val jwtUser = auth.principal as JwtUser
                return jwtUser.id
            }
            throw WrongCredentialsException("У вас не прав на просмотр данной информации")
        }
}
