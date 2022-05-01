package com.bloss.userinfoservice.model

data class Archive(
    private val user: User,
    private val posts: List<Post>
)
