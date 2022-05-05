package com.bloss.mainservice.service

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.LongSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

@Service
class MessageService(
    @Value("\${spring.kafka.template.default-topic}")
    private val topicName: String,
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    private lateinit var producer: KafkaProducer<String, Long>

    @PostConstruct
    fun init() {
        val props = Properties()
        props["bootstrap.servers"] = bootstrapServers
        producer = KafkaProducer(props, StringSerializer(), LongSerializer())
    }

    fun send(message: Long) {
        producer.send(ProducerRecord(topicName, message))
    }
}