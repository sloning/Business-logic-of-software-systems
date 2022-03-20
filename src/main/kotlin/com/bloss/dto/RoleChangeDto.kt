package com.bloss.dto

import com.bloss.model.Role
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class RoleChangeDto(
    @field:PositiveOrZero
    val userId: Long,
    @field:NotNull
    val newRole: Role
)
