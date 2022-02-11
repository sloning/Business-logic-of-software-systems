package com.bloss.model

import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "`user`")
data class User(
    @Id @GeneratedValue
    var id: Long = -1,
    @Email
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var firstName: String,
    @Column(nullable = false)
    var secondName: String
)
