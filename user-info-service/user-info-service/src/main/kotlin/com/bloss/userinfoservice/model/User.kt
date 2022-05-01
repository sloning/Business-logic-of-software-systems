package com.bloss.userinfoservice.model

import javax.persistence.*

@Entity
@Table(name = "`user`")
data class User(
    @Id @GeneratedValue
    var id: Long = -1,
    @Column(unique = true)
    var email: String,
    @Column
    var password: String,
    @Column(name = "firstname")
    var firstName: String,
    @Column(name = "secondname")
    var secondName: String,
    @Column
    @Enumerated
    var role: Role = Role.ROLE_USER,
    @Column(name = "userstatus")
    @Enumerated
    var userStatus: UserStatus = UserStatus.ACTIVE
)

enum class Role { ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR }
enum class UserStatus { LOCKED, BANNED, ACTIVE, DELETED }
