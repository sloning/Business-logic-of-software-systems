package com.bloss.controller

import com.bloss.dto.LoginDto
import com.bloss.dto.RegisterDto
import com.bloss.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginDto: LoginDto) = userService.authenticateUser(loginDto)

    @PostMapping("/register")
    fun register(@Valid @RequestBody registerDto: RegisterDto) = userService.registerUser(registerDto)
}