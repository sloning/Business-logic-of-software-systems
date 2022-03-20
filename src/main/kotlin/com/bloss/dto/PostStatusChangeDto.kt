package com.bloss.dto

import com.bloss.model.PostStatus
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class PostStatusChangeDto(
    @field:PositiveOrZero
    val postId: Long,
    @field:NotNull
    val newPostStatus: PostStatus
)
