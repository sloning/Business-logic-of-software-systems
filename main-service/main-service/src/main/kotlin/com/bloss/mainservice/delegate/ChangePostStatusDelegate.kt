package com.bloss.mainservice.delegate

import com.bloss.mainservice.dto.PostStatusChangeDto
import com.bloss.mainservice.model.PostStatus
import com.bloss.mainservice.service.PostService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("changePostStatusDelegate")
class ChangePostStatusDelegate(private val postService: PostService) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val postId = execution.getVariable("postId") as Long
        val newPostStatus = PostStatus.valueOf(execution.getVariable("newPostStatus") as String)
        val postStatusChangeDto = PostStatusChangeDto(postId, newPostStatus)

        postService.changeStatus(postStatusChangeDto)
    }
}