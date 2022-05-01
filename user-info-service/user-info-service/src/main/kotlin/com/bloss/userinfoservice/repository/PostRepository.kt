package com.bloss.userinfoservice.repository

import com.bloss.userinfoservice.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByUserId(userId: Long): List<Post>
}