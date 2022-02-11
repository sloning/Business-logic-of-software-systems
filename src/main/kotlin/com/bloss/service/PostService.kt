package com.bloss.service

import com.bloss.model.Post
import com.bloss.repository.PostRepository
import com.bloss.security.AuthenticationFacade
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val authenticationFacade: AuthenticationFacade
) {
    fun findAll(pageable: Pageable): Page<Post> {
        return postRepository.findAll(pageable)
    }

    fun create(post: Post): Post {
        post.userId = authenticationFacade.userId
        return postRepository.save(post)
    }

    fun update(post: Post): Post {
        return postRepository.save(post)
    }

    fun delete(post: Post) {
        postRepository.delete(post)
    }
}