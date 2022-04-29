package com.bloss.mainservice.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterDto(
    @field:NotBlank
    @field:Email
    val email: String,
    @field:Size(min = 8)
    val password: String,
    @field:NotBlank
    val firstName: String,
    @field:NotBlank
    val secondName: String
)
