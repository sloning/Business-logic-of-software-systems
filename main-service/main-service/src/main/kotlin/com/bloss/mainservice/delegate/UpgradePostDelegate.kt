package com.bloss.mainservice.delegate

import com.bloss.mainservice.exception.BadRequestException
import com.bloss.mainservice.service.PostService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("upgradePostDelegate")
class UpgradePostDelegate(
    private val postService: PostService,
) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val postId = execution.getVariable("postId") as Long
        try {
            postService.makePostPaid(postId)
            execution.setVariable("status", true)
        } catch (e: BadRequestException) {
            execution.setVariable("status", false)
        }
    }
}
