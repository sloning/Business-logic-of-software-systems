package com.bloss.repository

import com.bloss.model.Post
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : PagingAndSortingRepository<Post, Long> {
}