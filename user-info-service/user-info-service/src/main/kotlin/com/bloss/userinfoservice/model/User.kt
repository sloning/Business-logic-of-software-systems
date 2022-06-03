package com.bloss.userinfoservice.model

import javax.persistence.*

@Entity
@Table(name = "`user`")
data class User(
    @Id @GeneratedValue
    var id: String = "",
    @Column(unique = true)
    var email: String,
    @Column
    var password: String,
    @Column(name = "firstname")
    var firstName: String,
    @Column(name = "secondname")
    var secondName: String,
)

enum class Role { ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR }
enum class UserStatus { LOCKED, BANNED, ACTIVE, DELETED }
