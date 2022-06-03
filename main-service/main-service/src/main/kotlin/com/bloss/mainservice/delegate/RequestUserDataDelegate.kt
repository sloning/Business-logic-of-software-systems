package com.bloss.mainservice.delegate

import com.bloss.mainservice.service.UserService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("requestuserdatadelegate")
class RequestUserDataDelegate(
    private val userService: UserService,
) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        userService.requestUserData()
    }
}