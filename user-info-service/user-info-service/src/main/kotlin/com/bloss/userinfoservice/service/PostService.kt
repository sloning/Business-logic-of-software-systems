package com.bloss.userinfoservice.service

import com.bloss.userinfoservice.model.Post
import com.bloss.userinfoservice.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
) {
    fun findAllByUserId(userId: String): List<Post> {
        return postRepository.findAllByUserId(userId)
    }
}