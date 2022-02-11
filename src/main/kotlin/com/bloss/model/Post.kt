package com.bloss.model

import javax.persistence.*

@Entity
data class Post(
    @Id @GeneratedValue
    var id: Long = -1,
    @Column
    var name: String,
    @Column
    var description: String,
    @Column
    var address: String,
    @Column
    var price: Int,
    @Column
    var image: String = "",
    @Column
    @Enumerated(EnumType.STRING)
    var typeOfPost: TypeOfPost,
    @Column
    @Enumerated(EnumType.STRING)
    var typeOfEstate: TypeOfEstate,
    @Column
    var userId: Long = -1,
    @Column
    var phoneNumber: String
)

enum class TypeOfPost { SALE, RENT }
enum class TypeOfEstate { LAND, HOUSE, FLAT, ROOM }
