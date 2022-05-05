package com.bloss.mainservice.config

import com.rabbitmq.jms.admin.RMQConnectionFactory
import com.rabbitmq.jms.admin.RMQDestination
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import javax.jms.ConnectionFactory
import javax.jms.Destination

@EnableJms
@Configuration
class JmsConfig {
    private val queueName = "defaultQueue"

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = RMQConnectionFactory()
        connectionFactory.username = "guest"
        connectionFactory.password = "guest"
        connectionFactory.virtualHost = "/"
        connectionFactory.host = "[::1]"
        connectionFactory.port = 5672
        return connectionFactory
    }

    @Bean
    fun jmsTemplate(): JmsTemplate {
        val template = JmsTemplate()
        template.connectionFactory = connectionFactory()
        template.setDeliveryPersistent(true)
        template.defaultDestination = destination()
        return template
    }

    @Bean
    fun destination(): Destination {
        return RMQDestination(queueName, true, false)
    }
}