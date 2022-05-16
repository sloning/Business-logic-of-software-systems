package com.bloss.mainservice.service

import com.bloss.mainservice.dto.LoginDto
import com.bloss.mainservice.dto.RegisterDto
import com.bloss.mainservice.dto.RoleChangeDto
import com.bloss.mainservice.dto.UserStatusChangeDto
import com.bloss.mainservice.exception.BadRequestException
import com.bloss.mainservice.exception.EntityAlreadyExists
import com.bloss.mainservice.exception.WrongCredentialsException
import com.bloss.mainservice.model.User
import com.bloss.mainservice.model.UserStatus
import com.bloss.mainservice.repository.UserRepository
import com.bloss.mainservice.repository.UserXmlRepository
import com.bloss.mainservice.security.AuthenticationFacade
import com.bloss.mainservice.security.JwtTokenProvider
import com.bloss.mainservice.util.UserDataProducer
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val authManager: AuthenticationManager,
    private val userDbRepository: UserRepository,
    private val authenticationFacade: AuthenticationFacade,
    private val userDataProducer: UserDataProducer
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
        userDbRepository.save(user)
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

    fun requestUserData() {
        val userId = authenticationFacade.userId
        userDataProducer.sendUserDataRequest(userId.toString())
    }
}