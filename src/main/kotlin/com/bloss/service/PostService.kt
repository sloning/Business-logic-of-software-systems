package com.bloss.service

import com.bloss.dto.PostStatusChangeDto
import com.bloss.exception.BadRequestException
import com.bloss.model.Post
import com.bloss.model.PostStatus
import com.bloss.repository.PostRepository
import com.bloss.security.AuthenticationFacade
import com.bloss.util.AutoModerator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val authenticationFacade: AuthenticationFacade,
    private val autoModerator: AutoModerator
) {
    fun findAll(pageable: Pageable): Page<Post> {
        return postRepository.findAllByStatus(PostStatus.ACTIVE, pageable)
    }

    fun findAllByStatus(status: String, pageable: Pageable): Page<Post> {
        return postRepository.findAllByStatus(enumValueOf(status), pageable)
    }

    fun findById(postId: Long): Post {
        return postRepository.findByIdOrNull(postId) ?: throw BadRequestException("Объявление не найдено")
    }

    fun create(post: Post): Post {
        post.userId = authenticationFacade.userId

        return validateAndSave(post)
    }

    fun update(post: Post): Post {
        return validateAndSave(post)
    }

    private fun validateAndSave(post: Post): Post {
        validate(post)
        return save(post)
    }

    private fun save(post: Post): Post {
        val savedPost = postRepository.save(post)

        return if (post.status == PostStatus.ACTIVE) savedPost
        else throw BadRequestException("Ваше объявление содержит плохие слова. Айайайайай")
    }

    private fun validate(post: Post): Post {
        if (autoModerator.validate(post)) post.status = PostStatus.HIDDEN
        else post.status = PostStatus.ACTIVE

        return post
    }

    fun delete(post: Post) {
        post.status = PostStatus.DELETED
        postRepository.save(post)
    }

    fun changeStatus(postStatusChangeDto: PostStatusChangeDto): Post {
        val post = findById(postStatusChangeDto.postId)
        post.status = postStatusChangeDto.newPostStatus

        return postRepository.save(post)
    }
}