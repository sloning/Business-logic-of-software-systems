package com.bloss.mainservice.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class LoginDto(
    @field:NotBlank
    @field:Email
    val email: String,
    @field:Size(min = 4)
    val password: String
)
