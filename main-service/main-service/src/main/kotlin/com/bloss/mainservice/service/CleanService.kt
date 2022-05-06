package com.bloss.mainservice.service

import com.bloss.mainservice.config.AppProperties
import com.bloss.mainservice.model.PostStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class CleanService(
    private val postService: PostService,
    private val appProperties: AppProperties
) {

    fun hideOldPosts() {
        hidePostsByCreationDate()
        hidePostsByLastWatchedDate()
    }

    private fun hidePostsByCreationDate() {
        val timeToHide = appProperties.schedule.millisecondsToHideByCreationDate
        val dateOfPostCreation = Date(Date().time - timeToHide)
        val posts = postService.findAllByDateAddedBefore(dateOfPostCreation)
        posts.forEach {
            it.status = PostStatus.HIDDEN
            postService.save(it)
        }
    }

    private fun hidePostsByLastWatchedDate() {
        val timeToHide = appProperties.schedule.millisecondsToHideByLastWatchedDate
        val dateOfPostLastWatch = Date(Date().time - timeToHide)
        val posts = postService.findAllByLastWatchedDateBefore(dateOfPostLastWatch)
        posts.forEach {
            it.status = PostStatus.HIDDEN
            postService.save(it)
        }
    }
}