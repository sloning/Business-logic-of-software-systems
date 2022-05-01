package com.bloss.userinfoservice.util

import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import java.io.File
import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@Component
class EmailUtil(
    private val sender: JavaMailSender
) {
    fun sendMessage(
        to: String,
        subject: String,
        body: String
    ) {
        sender.send {
            it.setRecipient(Message.RecipientType.TO, InternetAddress(to))
            it.subject = subject
            it.setText(body)
        }
    }

    fun sendMessageWithAttachment(
        to: String,
        subject: String,
        text: String,
        pathToAttachment: String
    ) {
        val message: MimeMessage = sender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text)
        val file = FileSystemResource(File(pathToAttachment))
        helper.addAttachment("Data.json", file)

        sender.send(message)
    }
}