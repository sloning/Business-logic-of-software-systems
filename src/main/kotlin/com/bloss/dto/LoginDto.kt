package com.bloss.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginDto(
    @NotBlank
    @Email
    val email: String,
    @Length(min = 4)
    val password: String
)
