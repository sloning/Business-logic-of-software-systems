package com.bloss.exception

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException(msg: String) : AuthenticationException(msg) {
}
