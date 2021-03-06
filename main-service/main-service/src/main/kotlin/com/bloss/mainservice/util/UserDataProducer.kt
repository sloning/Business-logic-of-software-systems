package com.bloss.mainservice.util

import com.rabbitmq.jms.admin.RMQConnectionFactory
import org.springframework.stereotype.Component
import javax.jms.ConnectionFactory
import javax.jms.MessageProducer
import javax.jms.Session

@Component
class UserDataProducer(private val appProperties: com.bloss.mainservice.config.AppProperties) {
    private val queueName = "defaultQueue"
    private lateinit var factory: ConnectionFactory

    init {
        factory = initFactory()
    }

    private fun initFactory(): ConnectionFactory {
        val factory = RMQConnectionFactory()
        factory.username = appProperties.rmq.rmqUsername
        factory.password = appProperties.rmq.rmqPassword
        factory.host = appProperties.rmq.rmqHost
        factory.port = appProperties.rmq.rmqPort
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
