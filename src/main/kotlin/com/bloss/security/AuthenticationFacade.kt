package com.bloss.security

import com.bloss.exception.WrongCredentialsException
import com.bloss.service.UserService
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade(private val userService: UserService) {
    val userId: Long
        get() {
            val auth = SecurityContextHolder.getContext().authentication
            if (auth !is AnonymousAuthenticationToken) {
                val jwtUser = auth.principal as JwtUser
                if (userService.isUserExists(jwtUser.id)) {
                    return jwtUser.id
                }
            }
            throw WrongCredentialsException("У вас не прав на просмотр данной информации")
        }
}
