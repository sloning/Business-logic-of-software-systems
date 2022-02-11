package com.bloss.repository

import com.bloss.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean
}