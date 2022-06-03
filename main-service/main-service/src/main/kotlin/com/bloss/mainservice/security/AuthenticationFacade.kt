package com.bloss.mainservice.security

import org.camunda.bpm.engine.IdentityService
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade(private val identityService: IdentityService) {
    val userId: String
        get() {
            return identityService.currentAuthentication.userId
        }
}
