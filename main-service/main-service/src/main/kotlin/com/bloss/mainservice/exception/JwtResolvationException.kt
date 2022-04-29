package com.bloss.mainservice.exception

import org.springframework.security.core.AuthenticationException

class JwtResolvationException(msg: String) : AuthenticationException(msg) {
}