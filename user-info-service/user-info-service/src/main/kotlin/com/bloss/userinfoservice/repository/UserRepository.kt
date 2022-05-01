package com.bloss.userinfoservice.repository

import com.bloss.userinfoservice.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}