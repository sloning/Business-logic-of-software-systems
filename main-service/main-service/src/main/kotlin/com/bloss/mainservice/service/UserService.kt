package com.bloss.mainservice.service

import com.bloss.mainservice.security.AuthenticationFacade
import com.bloss.mainservice.util.UserDataProducer
import org.springframework.stereotype.Service

@Service
class UserService(
    private val authenticationFacade: AuthenticationFacade,
    private val userDataProducer: UserDataProducer
) {
    fun requestUserData() {
        val userId = authenticationFacade.userId
        userDataProducer.sendUserDataRequest(userId)
    }
}