package com.bloss.mainservice.util

import com.bloss.mainservice.service.CleanService
import org.quartz.Job
import org.quartz.JobExecutionContext

class CleanJob(private val cleanService: CleanService) : Job {
    override fun execute(context: JobExecutionContext) {
        cleanService.hideOldPosts()
    }
}