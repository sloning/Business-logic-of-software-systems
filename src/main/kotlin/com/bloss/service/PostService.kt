package com.bloss.service

import com.bloss.exception.BadRequestException
import com.bloss.model.Post
import com.bloss.repository.PostRepository
import com.bloss.security.AuthenticationFacade
import com.bloss.util.AutoModerator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val authenticationFacade: AuthenticationFacade,
    private val autoModerator: AutoModerator
) {
    fun findAll(pageable: Pageable): Page<Post> {
        return postRepository.findAll(pageable)
    }

    fun create(post: Post): Post {
        validate(post)
        post.userId = authenticationFacade.userId

        return postRepository.save(post)
    }

    fun update(post: Post): Post {
        validate(post)
        return postRepository.save(post)
    }

    private fun validate(post: Post) {
        if (autoModerator.validate(post)) {
            throw BadRequestException("Ваше объявление содержит плохие слова. Айайайайай")
        }
    }

    fun delete(post: Post) {
        postRepository.delete(post)
    }
}