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
    @Column(name = "type_of_post")
    @Enumerated(EnumType.STRING)
    var typeOfPost: TypeOfPost,
    @Column
    @Enumerated(EnumType.STRING)
    var status: PostStatus = PostStatus.HIDDEN,
    @Column(name = "type_of_estate")
    @Enumerated(EnumType.STRING)
    var typeOfEstate: TypeOfEstate,
    @Column(name = "user_id")
    var userId: String = "",
    @Column(name = "phone_number")
    var phoneNumber: String,
    @Column(name = "is_paid")
    var isPaid: Boolean,
    @Column(name = "date_added", nullable = false)
    var dateAdded: Date = Date(),
    @Column(name = "last_watched", nullable = false)
    var lastWatched: Date = Date()
)

enum class TypeOfPost { SALE, RENT }
enum class TypeOfEstate { LAND, HOUSE, FLAT, ROOM }
enum class PostStatus { ACTIVE, HIDDEN, DELETED }
