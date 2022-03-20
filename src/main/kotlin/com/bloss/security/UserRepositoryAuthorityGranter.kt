package com.bloss.security

import com.bloss.model.Role
import com.bloss.repository.UserXmlRepository
import org.springframework.security.authentication.jaas.AuthorityGranter
import java.security.Principal

class UserRepositoryAuthorityGranter : AuthorityGranter {
    override fun grant(principal: Principal): Set<String> {
        val userRepository = UserXmlRepository
        val role: Role? = userRepository.findRoleByEmail(principal.name)
        return mutableSetOf(role.toString())
    }
}
