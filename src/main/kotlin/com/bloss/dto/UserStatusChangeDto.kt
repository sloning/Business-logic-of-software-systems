package com.bloss.dto

import com.bloss.model.UserStatus
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class UserStatusChangeDto(
    @field:PositiveOrZero
    val userId: Long,
    @field:NotNull
    val newUserStatus: UserStatus
)
