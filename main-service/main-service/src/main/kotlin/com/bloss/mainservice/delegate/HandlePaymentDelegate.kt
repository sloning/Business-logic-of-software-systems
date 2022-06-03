package com.bloss.mainservice.delegate

import com.bloss.mainservice.security.AuthenticationFacade
import com.bloss.mainservice.service.PaymentService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("handlePaymentDelegate")
class HandlePaymentDelegate(
    private val paymentService: PaymentService,
    private val authenticationFacade: AuthenticationFacade
) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val userId = authenticationFacade.userId
        if (paymentService.processPayment(userId)) {
            execution.setVariable("status", true)
        } else {
            execution.setVariable("status", false)
        }
    }
}
