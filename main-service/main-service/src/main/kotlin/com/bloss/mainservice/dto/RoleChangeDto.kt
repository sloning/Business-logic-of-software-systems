package com.bloss.mainservice.dto

import com.bloss.mainservice.model.Role
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class RoleChangeDto(
    @field:PositiveOrZero
    val userId: Long,
    @field:NotNull
    val newRole: Role
)
