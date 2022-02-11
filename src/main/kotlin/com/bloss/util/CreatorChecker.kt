package com.bloss.util

import com.bloss.exception.BadRequestException
import com.bloss.model.Post
import com.bloss.repository.PostRepository
import com.bloss.security.AuthenticationFacade
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