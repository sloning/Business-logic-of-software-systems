package com.bloss.mainservice.util

import com.rabbitmq.jms.admin.RMQConnectionFactory
import org.springframework.stereotype.Component
import javax.jms.ConnectionFactory
import javax.jms.MessageProducer
import javax.jms.Session

@Component
class UserDataProducer {
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
        return factory
    }

    fun sendUserDataRequest(userId: String) {
        val connection = factory.createConnection()
        val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

        try {
            val queue = session.createQueue(queueName)

            val producer: MessageProducer = session.createProducer(queue)
            val msg = session.createTextMessage(userId)
            producer.send(msg)
        } finally {
            connection?.close()
            session?.close()
        }
    }
}