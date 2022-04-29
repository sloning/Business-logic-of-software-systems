package com.bloss.mainservice.security

import com.bloss.mainservice.model.Role
import com.bloss.mainservice.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class JwtUser(
    val id: Long,
    val email: String?,
    private val password: String?,
    val role: Role
) : UserDetails {
    companion object {
        fun create(userId: Long, role: Role): JwtUser {
            return JwtUser(
                userId,
                null,
                null,
                role
            )
        }

        fun create(user: User): JwtUser {
            return JwtUser(
                user.id,
                user.email,
                user.password,
                user.role
            )
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
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
