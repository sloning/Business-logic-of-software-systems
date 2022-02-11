package com.bloss.model

import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "`user`")
data class User(
    @Id @GeneratedValue
    var id: Long = -1,
    @Email
    @Column(unique = true)
    var email: String,
    @Column
    var password: String,
    @Column
    var firstName: String,
    @Column
    var secondName: String
)
