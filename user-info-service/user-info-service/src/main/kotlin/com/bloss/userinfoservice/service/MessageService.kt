package com.bloss.userinfoservice.service

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*
import javax.annotation.PostConstruct

@Service
class MessageService(
    @Value("\${spring.kafka.template.default-topic}")
    private val topicName: String,
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String,
    @Value("\${spring.kafka.consumer.group-id}")
    private val group: String,
    private val archiveService: ArchiveService
) {
    private lateinit var consumer: KafkaConsumer<String, Long>

    @PostConstruct
    fun init() {
        val props = Properties()
        props.setProperty("bootstrap.servers", bootstrapServers)
        props.setProperty("group.id", group)
        props.setProperty("enable.auto.commit", "true")
        props.setProperty("auto.commit.interval.ms", "1000")

        consumer = KafkaConsumer(props, StringDeserializer(), LongDeserializer())
        consumer.subscribe(listOf(topicName))

        consume()
    }

    private fun consume() {
        while (true) {
            val messages = consumer.poll(Duration.ofMillis(100))
            for (message in messages) {
                archiveService.makeArchive(message.value())
            }
        }
    }
}