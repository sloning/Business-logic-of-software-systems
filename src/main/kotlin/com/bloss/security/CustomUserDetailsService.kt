package com.bloss.security

import com.bloss.model.User
import com.bloss.repository.UserXmlRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CustomUserDetailsService : UserDetailsService {
    private val userRepository: UserXmlRepository = UserXmlRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByEmail(username) ?: throw EntityNotFoundException("Пользователь не найден")
        return JwtUser.create(user)
    }
}