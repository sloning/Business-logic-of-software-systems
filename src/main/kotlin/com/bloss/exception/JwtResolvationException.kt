package com.bloss.exception

import org.springframework.security.core.AuthenticationException

class JwtResolvationException(msg: String) : AuthenticationException(msg) {
}