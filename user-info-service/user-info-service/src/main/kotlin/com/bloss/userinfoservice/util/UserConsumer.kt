package com.bloss.userinfoservice.util

import com.rabbitmq.jms.admin.RMQConnectionFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import javax.jms.ConnectionFactory
import javax.jms.Queue
import javax.jms.Session

@Component
class UserConsumer(private val userMessageListener: UserMessageListener) {
    private val queueName = "defaultQueue"
    private lateinit var factory: ConnectionFactory

    init {
        factory = initFactory()
    }

    private fun initFactory(): ConnectionFactory {
        val factory = RMQConnectionFactory()
        factory.username = "guest"
        factory.password = "guest"
        factory.host = "[::1]"
        factory.port = 5672
        factory.onMessageTimeoutMs = 10000
        return factory
    }

    @Scheduled(fixedDelay = 1000)
    private fun checkForNewMessages() {
        val connection = factory.createConnection()
        val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

        try {
            val queue: Queue = session.createQueue(queueName)

            val consumer = session.createConsumer(queue)
            consumer.messageListener = userMessageListener
            connection.start()
        } finally {
            session.close()
            connection.close()
        }
    }
}