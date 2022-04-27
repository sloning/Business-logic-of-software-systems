package com.bloss.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

@Entity
data class Post(
    @Id @GeneratedValue
    var id: Long = -1,
    @Column
    @field:NotBlank(message = "Название не может быть пустым")
    var name: String,
    @Column
    @field:NotBlank(message = "Описание не может быть пустым")
    var description: String,
    @Column
    @field:NotBlank(message = "Адрес не может быть пустым")
    var address: String,
    @Column
    @field:Positive(message = "Цена не может быть <= 0")
    var price: Int,
    @Column
    var image: String = "",
    @Column
    @Enumerated(EnumType.STRING)
    var typeOfPost: TypeOfPost,
    @Column
    @Enumerated(EnumType.STRING)
    var status: PostStatus = PostStatus.HIDDEN,
    @Column
    @Enumerated(EnumType.STRING)
    var typeOfEstate: TypeOfEstate,
    @Column
    var userId: Long = -1,
    @Column
    @field:Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})\$", message = "Неверный номер телефона")
    var phoneNumber: String,
    @Column
    var isPaid: Boolean,
    @Column(nullable = false)
    var dateAdded: Date = Date(),
    @Column(nullable = false)
    var lastWatched: Date = Date()
)

enum class TypeOfPost { SALE, RENT }
enum class TypeOfEstate { LAND, HOUSE, FLAT, ROOM }
enum class PostStatus { ACTIVE, HIDDEN, DELETED }
