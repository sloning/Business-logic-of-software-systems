package com.bloss.mainservice.delegate

import com.bloss.mainservice.service.PostService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("getPostsDelegate")
class GetPostsDelegate(private val postService: PostService) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val postsList = postService.findAll()
        execution.setVariable("postsList", postsList)
    }
}
