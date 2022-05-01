package com.bloss.userinfoservice.model

import java.util.*
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
    @Column(name = "typeofpost")
    @Enumerated(EnumType.STRING)
    var typeOfPost: TypeOfPost,
    @Column
    @Enumerated(EnumType.STRING)
    var status: PostStatus = PostStatus.HIDDEN,
    @Column(name = "typeofestate")
    @Enumerated(EnumType.STRING)
    var typeOfEstate: TypeOfEstate,
    @Column(name = "userid")
    var userId: Long = -1,
    @Column(name = "phonenumber")
    var phoneNumber: String,
    @Column(name = "ispaid")
    var isPaid: Boolean,
    @Column(name = "dateadded", nullable = false)
    var dateAdded: Date = Date(),
    @Column(name = "lastwatched", nullable = false)
    var lastWatched: Date = Date()
)

enum class TypeOfPost { SALE, RENT }
enum class TypeOfEstate { LAND, HOUSE, FLAT, ROOM }
enum class PostStatus { ACTIVE, HIDDEN, DELETED }
