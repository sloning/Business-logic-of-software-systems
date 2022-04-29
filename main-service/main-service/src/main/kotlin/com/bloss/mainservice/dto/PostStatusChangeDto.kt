package com.bloss.mainservice.dto

import com.bloss.mainservice.model.PostStatus
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class PostStatusChangeDto(
    @field:PositiveOrZero
    val postId: Long,
    @field:NotNull
    val newPostStatus: PostStatus
)
