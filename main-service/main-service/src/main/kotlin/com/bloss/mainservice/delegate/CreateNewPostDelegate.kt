package com.bloss.mainservice.delegate

import com.bloss.mainservice.exception.BadRequestException
import com.bloss.mainservice.model.Post
import com.bloss.mainservice.model.TypeOfEstate
import com.bloss.mainservice.model.TypeOfPost
import com.bloss.mainservice.service.PostService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("createNewPostDelegate")
class CreateNewPostDelegate(private val postService: PostService) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val post = Post(
            name = execution.getVariable("postName") as String,
            description = execution.getVariable("postDescription") as String,
            address = execution.getVariable("postAddress") as String,
            price = execution.getVariable("postPrice") as Int,
            typeOfPost = TypeOfPost.valueOf(execution.getVariable("typeOfPost") as String),
            typeOfEstate = TypeOfEstate.valueOf(execution.getVariable("typeOfEstate") as String),
            phoneNumber = execution.getVariable("postPhoneNumber") as String,
            isPaid = false
        )
        try {
            postService.create(post)
            execution.setVariable("validationResult", true)
        } catch (e: BadRequestException) {
            execution.setVariable("validationResult", false)
        }
    }
}
