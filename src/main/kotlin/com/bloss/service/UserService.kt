package com.bloss.service

import com.bloss.dto.LoginDto
import com.bloss.dto.RegisterDto
import com.bloss.dto.RoleChangeDto
import com.bloss.dto.UserStatusChangeDto
import com.bloss.exception.BadRequestException
import com.bloss.exception.EntityAlreadyExists
import com.bloss.exception.WrongCredentialsException
import com.bloss.model.User
import com.bloss.model.UserStatus
import com.bloss.repository.UserXmlRepository
import com.bloss.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val authManager: AuthenticationManager,
) {
    private val userRepository: UserXmlRepository = UserXmlRepository

    fun authenticateUser(loginDto: LoginDto): Map<String, String> {
        val authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.email, loginDto.password
            )
        )

        val user = userRepository.findByEmail(loginDto.email)
        if (!authentication.isAuthenticated || user == null || user.userStatus != UserStatus.ACTIVE)
            throw WrongCredentialsException("Неверные данные")

        return getToken(user)
    }

    fun registerUser(registerDto: RegisterDto): Map<String, String> {
        if (userRepository.existsByEmail(registerDto.email)) {
            throw EntityAlreadyExists("Пользователь с таким email адресом уже существует")
        }

        var user = User(
            email = registerDto.email,
            password = passwordEncoder.encode(registerDto.password),
            firstName = registerDto.firstName,
            secondName = registerDto.secondName
        )
        user = save(user)

        return getToken(user)
    }

    private fun getToken(user: User): Map<String, String> {
        val token: String = jwtTokenProvider.createToken(user.id, user.role)
        val response: MutableMap<String, String> = HashMap()
        response["token"] = token
        response["userId"] = user.id.toString()
        response["role"] = user.role.toString()
        return response
    }

    private fun save(user: User): User {
        return userRepository.save(user)
    }

    fun isUserExists(userId: Long): Boolean {
        return userRepository.existsById(userId)
    }

    fun findById(userId: Long): User {
        return userRepository.findById(userId) ?: throw BadRequestException("Пользователь не найден")
    }

    fun changeRole(roleChangeDto: RoleChangeDto): User {
        val user = findById(roleChangeDto.userId)
        user.role = roleChangeDto.newRole

        return save(user)
    }

    fun changeStatus(userStatusChangeDto: UserStatusChangeDto): User {
        val user = findById(userStatusChangeDto.userId)
        user.userStatus = userStatusChangeDto.newUserStatus

        return save(user)
    }
}