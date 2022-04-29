package com.bloss.mainservice.util

import com.bloss.mainservice.exception.BadRequestException
import com.bloss.mainservice.model.Post
import com.bloss.mainservice.repository.PostRepository
import com.bloss.mainservice.security.AuthenticationFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CreatorChecker(
    private val authenticationFacade: AuthenticationFacade,
    private val postRepository: PostRepository
) {
    fun check(post: Post): Boolean {
        val dbPost: Post = postRepository.findByIdOrNull(post.id)
            ?: throw BadRequestException("Объявления с id: ${post.id} не существует")

        return dbPost.userId == authenticationFacade.userId
    }
}