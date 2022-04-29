package com.bloss.mainservice.config

import com.atomikos.icatch.jta.UserTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.jta.JtaTransactionManager

@Configuration
@EnableTransactionManagement
class TransactionConfig {
    @Bean(initMethod = "init", destroyMethod = "close")
    fun userTransactionManager() =
        UserTransactionManager().apply {
            setTransactionTimeout(300)
            forceShutdown = true
        }

    @Bean
    fun transactionManager() =
        JtaTransactionManager().apply {
            val trans = userTransactionManager()
            transactionManager = trans
            userTransaction = trans
        }
}
