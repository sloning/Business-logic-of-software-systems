package com.bloss.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class JwtUser(
    private val id: Long,
    private val email: String?,
    private val password: String?,
) : UserDetails {
    companion object {
        fun create(userId: String): JwtUser {
            return JwtUser(
                userId.toLong(),
                null,
                null
            )
        }

        fun create(userId: Long): JwtUser {
            return JwtUser(
                userId,
                null,
                null
            )
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return HashSet()
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
