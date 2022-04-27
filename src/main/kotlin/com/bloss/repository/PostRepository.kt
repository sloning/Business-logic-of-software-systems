package com.bloss.repository

import com.bloss.model.Post
import com.bloss.model.PostStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostRepository : PagingAndSortingRepository<Post, Long> {
    fun findAllByStatus(status: PostStatus, pageable: Pageable): Page<Post>

    fun findAllByIsPaidTrue(pageable: Pageable): Page<Post>

    fun findAllByLastWatchedBefore(date: Date): List<Post>

    fun findAllByDateAddedBefore(date: Date): List<Post>
}