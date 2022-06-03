package com.bloss.mainservice.service

import com.bloss.mainservice.dto.PostStatusChangeDto
import com.bloss.mainservice.exception.BadRequestException
import com.bloss.mainservice.model.Post
import com.bloss.mainservice.model.PostStatus
import com.bloss.mainservice.repository.PostRepository
import com.bloss.mainservice.security.AuthenticationFacade
import com.bloss.mainservice.util.AutoModerator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.support.TransactionTemplate
import java.util.*
import javax.annotation.PostConstruct

@Service
class PostService(
    private val postRepository: PostRepository,
    private val authenticationFacade: AuthenticationFacade,
    private val autoModerator: AutoModerator,
    private val transactionManager: PlatformTransactionManager,
    private val paymentService: PaymentService,
) {
    private lateinit var transactionTemplate: TransactionTemplate

    @PostConstruct
    fun init() {
        transactionTemplate = TransactionTemplate(transactionManager)
        transactionTemplate.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
    }

    fun findAll(): List<Post> {
        return postRepository.findAllByStatus(PostStatus.ACTIVE)
    }

    fun findAll(pageable: Pageable): Page<Post> {
        return postRepository.findAllByStatus(PostStatus.ACTIVE, pageable)
    }

    fun findAllByStatus(status: String, pageable: Pageable): Page<Post> {
        return postRepository.findAllByStatus(enumValueOf(status), pageable)
    }

    fun findById(postId: Long): Post {
        return postRepository.findByIdOrNull(postId) ?: throw BadRequestException("Объявление не найдено")
    }

    fun findPaid(pageable: Pageable): Page<Post> {
        return postRepository.findAllByIsPaidTrue(pageable)
    }

    fun findAllByLastWatchedDateBefore(date: Date): List<Post> {
        return postRepository.findAllByLastWatchedBefore(date)
    }

    fun findAllByDateAddedBefore(date: Date): List<Post> {
        return postRepository.findAllByDateAddedBefore(date)
    }

    fun getPostById(postId: Long): Post {
        val post = findById(postId)
        updateLastWatchedDate(post)
        return post
    }

    private fun updateLastWatchedDate(post: Post) {
        val modifiedPost = post.copy()
        modifiedPost.lastWatched = Date()
        save(modifiedPost)
    }

    fun create(post: Post): Post {
        post.userId = authenticationFacade.userId
        post.isPaid = false

        if (!validatePostAndSetStatus(post)) throw BadRequestException("Ваше объявление содержит плохие слова. Айайайайай")

        return save(post)
    }

    private fun validatePostAndSetStatus(post: Post): Boolean {
        return if (autoModerator.validate(post)) {
            post.status = PostStatus.ACTIVE
            true
        } else {
            post.status = PostStatus.HIDDEN
            false
        }
    }

    fun update(post: Post): Post {
        val dbPost: Post = findById(post.id)

        if (!autoModerator.validate(post)) throw BadRequestException("Ваше объявление содержит плохие слова. Айайайайай")

        post.status = dbPost.status
        post.isPaid = dbPost.isPaid
        post.userId = dbPost.userId

        return save(post)
    }

    fun makePostPaid(postId: Long): Post {
        var dbPost: Post = findById(postId)

        dbPost.isPaid = true
        dbPost = save(dbPost)

        return dbPost
    }

    fun upgradePost(postId: Long): Post {
        var dbPost: Post = findById(postId)

        transactionTemplate.execute {
            processPayment()
            dbPost.isPaid = true
            dbPost = save(dbPost)
        }

        return dbPost
    }

    fun upgradePost(post: Post): Post {
        return upgradePost(post.id)
    }

    private fun processPayment() {
        val userId = authenticationFacade.userId

        if (!paymentService.processPayment(userId)) throw BadRequestException("Ошибка оплаты")
    }

    fun save(post: Post): Post {
        return postRepository.save(post)
    }

    fun delete(post: Post) {
        post.status = PostStatus.DELETED
        postRepository.save(post)
    }

    fun changeStatus(postStatusChangeDto: PostStatusChangeDto): Post {
        val post = findById(postStatusChangeDto.postId)
        post.status = postStatusChangeDto.newPostStatus
        return save(post)
    }
}
