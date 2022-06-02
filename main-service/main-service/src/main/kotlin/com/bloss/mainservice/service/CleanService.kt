package com.bloss.mainservice.service

import com.bloss.mainservice.model.PostStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class CleanService(
    private val postService: PostService
) {
    fun hidePostsByCreationDate(timeToHide: Long) {
        println("called 1 $timeToHide")
        val dateOfPostCreation = Date(Date().time - timeToHide)
        val posts = postService.findAllByDateAddedBefore(dateOfPostCreation)
        posts.forEach {
            it.status = PostStatus.HIDDEN
            postService.save(it)
        }
    }

    fun hidePostsByLastWatchedDate(timeToHide: Long) {
        println("called 2 $timeToHide")
        val dateOfPostLastWatch = Date(Date().time - timeToHide)
        val posts = postService.findAllByLastWatchedDateBefore(dateOfPostLastWatch)
        posts.forEach {
            it.status = PostStatus.HIDDEN
            postService.save(it)
        }
    }
}