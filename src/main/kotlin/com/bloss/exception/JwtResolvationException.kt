package com.bloss.exception

import org.springframework.security.core.AuthenticationException

class JwtResolvationException : AuthenticationException {
    constructor(msg: String, cause: Throwable) : super(msg, cause) {}
    constructor(msg: String) : super(msg) {}
}