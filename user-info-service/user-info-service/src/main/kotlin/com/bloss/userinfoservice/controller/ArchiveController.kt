package com.bloss.userinfoservice.controller

import com.bloss.userinfoservice.service.ArchiveService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Controller

@Controller
class ArchiveController(private val archiveService: ArchiveService) {
    @KafkaListener(
        topics = ["user-data-request"],
        groupId = "user-info-service-1"
    )
    fun requestArchiveWithUserData(userId: Long) = archiveService.makeArchive(userId)
}