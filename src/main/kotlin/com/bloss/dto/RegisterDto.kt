package com.bloss.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class RegisterDto(
    @NotBlank
    @Email
    val email: String,
    @Length(min = 8)
    val password: String,
    @NotBlank
    val firstName: String,
    @NotBlank
    val secondName: String
)
