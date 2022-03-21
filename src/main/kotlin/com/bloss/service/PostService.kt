package com.bloss.service

import com.atomikos.icatch.jta.UserTransactionImp
import com.bloss.dto.PostStatusChangeDto
import com.bloss.exception.BadRequestException
import com.bloss.exception.CommitRolledBackException
import com.bloss.model.Post
import com.bloss.model.PostStatus
import com.bloss.repository.PostRepository
import com.bloss.security.AuthenticationFacade
import com.bloss.util.AutoModerator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED)
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
        val savedPost = save(post)

        return if (post.status == PostStatus.ACTIVE) savedPost
        else throw BadRequestException("Ваше объявление содержит плохие слова. Айайайайай")
    }

    private fun save(post: Post): Post {
        val utx = UserTransactionImp()
        var rollback = false
        var savedPost: Post? = null
        try {
            utx.begin()
            savedPost = postRepository.save(post)
        } catch (e: Exception) {
            rollback = true
        } finally {
            if (rollback) utx.rollback()
            else utx.commit()
        }
        return savedPost ?: throw CommitRolledBackException("Невозможно выполнить транзакцию")
    }

    private fun validate(post: Post): Post {
        if (autoModerator.validate(post)) post.status = PostStatus.HIDDEN
        else post.status = PostStatus.ACTIVE

        return post
    }

    fun delete(post: Post) {
        val utx = UserTransactionImp()
        var rollback = false
        try {
            utx.begin()
            post.status = PostStatus.DELETED
            postRepository.save(post)
        } catch (e: Exception) {
            rollback = true
        } finally {
            if (rollback) utx.rollback()
            else utx.commit()
        }
    }

    fun changeStatus(postStatusChangeDto: PostStatusChangeDto): Post {
        val post = findById(postStatusChangeDto.postId)
        post.status = postStatusChangeDto.newPostStatus
        return save(post)
    }
}