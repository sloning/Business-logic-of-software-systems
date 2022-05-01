package com.bloss.userinfoservice.service

import com.bloss.userinfoservice.model.User
import com.bloss.userinfoservice.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun findById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw EntityNotFoundException()
    }
}