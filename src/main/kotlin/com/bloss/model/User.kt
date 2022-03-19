package com.bloss.model

import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "`user`")
data class User(
    @Id @GeneratedValue
    var id: Long = -1,
    @Column(unique = true)
    @field:Email
    var email: String,
    @Column
    var password: String,
    @Column
    var firstName: String,
    @Column
    var secondName: String,
    @Column
    @Enumerated
    var role: ROLE = ROLE.ROLE_USER,
    @Column
    @Enumerated
    var status: STATUS = STATUS.ACTIVE
)

enum class ROLE { ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR }

enum class STATUS { LOCKED, BANNED, ACTIVE, DELETED }
