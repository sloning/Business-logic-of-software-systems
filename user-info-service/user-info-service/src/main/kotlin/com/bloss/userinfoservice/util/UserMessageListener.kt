package com.bloss.userinfoservice.util

import com.bloss.userinfoservice.service.ArchiveService
import org.springframework.stereotype.Component
import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.TextMessage

@Component
class UserMessageListener(private val archiveService: ArchiveService) : MessageListener {
    override fun onMessage(message: Message) {
        val textMessage: TextMessage = message as TextMessage
        archiveService.makeArchive(textMessage.text)
    }
}