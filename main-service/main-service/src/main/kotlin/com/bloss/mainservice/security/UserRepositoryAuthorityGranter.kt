package com.bloss.mainservice.security

import org.springframework.security.authentication.jaas.AuthorityGranter
import java.security.Principal

class UserRepositoryAuthorityGranter : AuthorityGranter {
    override fun grant(principal: Principal): Set<String> {
//        val userRepository = UserXmlRepository
//        val role: Role? = userRepository.findRoleByEmail(principal.name)
//        return mutableSetOf(role.toString())
        return mutableSetOf("asdf")
    }
}
