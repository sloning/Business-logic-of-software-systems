package com.bloss.service

import org.springframework.stereotype.Service

@Service
class PaymentService() {
    fun processPayment(userId: Long): Boolean {
        return true;
    }
}
