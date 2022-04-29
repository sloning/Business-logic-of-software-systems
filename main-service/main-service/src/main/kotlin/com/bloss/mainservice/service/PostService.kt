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
import org.springframework.scheduling.annotation.Scheduled
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
    private val appProperties: com.bloss.mainservice.config.AppProperties
) {
    private lateinit var transactionTemplate: TransactionTemplate

    @PostConstruct
    fun init() {
        transactionTemplate = TransactionTemplate(transactionManager)
        transactionTemplate.propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRES_NEW
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

    fun upgradePost(post: Post): Post {
        var dbPost: Post = findById(post.id)

        transactionTemplate.execute {
            processPayment()
            dbPost.isPaid = true
            dbPost = save(dbPost)
        }

        return dbPost
    }

    private fun processPayment() {
        val userId = authenticationFacade.userId

        if (!paymentService.processPayment(userId)) throw BadRequestException("Ошибка оплаты")
    }

    private fun save(post: Post): Post {
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

    @Scheduled(
        cron = "#{@'app-com.bloss.mainservice.config.AppProperties'.schedule.scheduleToHidePosts}",
        zone = "Europe/Moscow"
    )
    private fun hideOldPosts() {
        hidePostsByCreationDate()
        hidePostsByLastWatchedDate()
    }

    private fun hidePostsByCreationDate() {
        val timeToHide = appProperties.schedule.millisecondsToHideByCreationDate
        val dateOfPostCreation = Date(Date().time - timeToHide)
        val posts = findAllByDateAddedBefore(dateOfPostCreation)
        posts.forEach {
            it.status = PostStatus.HIDDEN
            save(it)
        }
    }

    private fun hidePostsByLastWatchedDate() {
        val timeToHide = appProperties.schedule.millisecondsToHideByLastWatchedDate
        val dateOfPostLastWatch = Date(Date().time - timeToHide)
        val posts = findAllByLastWatchedDateBefore(dateOfPostLastWatch)
        posts.forEach {
            it.status = PostStatus.HIDDEN
            save(it)
        }
    }
}
