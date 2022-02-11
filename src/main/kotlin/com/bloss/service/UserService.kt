package com.bloss.service

import com.bloss.dto.LoginDto
import com.bloss.dto.RegisterDto
import com.bloss.exception.BadRequestException
import com.bloss.model.User
import com.bloss.repository.UserRepository
import com.bloss.security.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {
    fun authenticateUser(loginDto: LoginDto): Map<String, String> {
        val user: User = userRepository.findByEmail(loginDto.email)
            ?: throw EntityNotFoundException("Пользователя с таким email адресом не существует")
        return getToken(user.id)
    }

    fun registerUser(registerDto: RegisterDto): Map<String, String> {
        if (userRepository.existsByEmail(registerDto.email)) {
            throw BadRequestException("Пользователь с таким email адресом уже существует")
        }

        var user = User(
            email = registerDto.email,
            password = passwordEncoder.encode(registerDto.password),
            firstName = registerDto.firstName,
            secondName = registerDto.secondName
        )
        user = userRepository.save(user)

        return getToken(user.id)
    }

    private fun getToken(userId: Long): Map<String, String> {
        val token: String = jwtTokenProvider.createToken(userId.toString())
        val response: MutableMap<String, String> = HashMap()
        response["token"] = token
        response["userId"] = userId.toString()
        return response
    }
}