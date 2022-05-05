package com.bloss.userinfoservice.controller

import com.bloss.userinfoservice.service.ArchiveService
import org.springframework.stereotype.Controller

@Controller
class ArchiveController(private val archiveService: ArchiveService) {
    fun requestArchiveWithUserData(userId: Long) = archiveService.makeArchive(userId)
}