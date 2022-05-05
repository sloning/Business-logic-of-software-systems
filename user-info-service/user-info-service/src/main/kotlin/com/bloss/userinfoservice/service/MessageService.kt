package com.bloss.userinfoservice.service

import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.jms.TextMessage
import kotlin.concurrent.thread

@Service
class MessageService(
    private val jmsTemplate: JmsTemplate,
    private val archiveService: ArchiveService
) {
    @PostConstruct
    fun init() = thread(start = true) {
        listen()
    }

    private fun listen() {
        while (true) {
            val message = jmsTemplate.receive()
            val textMessage: TextMessage
            if (message != null) {
                textMessage = message as TextMessage
                println(textMessage.text)
                archiveService.makeArchive(textMessage.text.toLong())
            }
        }
    }
}