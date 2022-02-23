package com.bloss.model

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

@Entity
data class Post(
    @Id @GeneratedValue
    var id: Long = -1,
    @Column
    @NotBlank(message = "Название не может быть пустым")
    var name: String,
    @Column
    @NotBlank(message = "Описание не может быть пустым")
    var description: String,
    @Column
    @NotBlank(message = "Адрес не может быть пустым")
    var address: String,
    @Column
    @Positive(message = "Цена не может быть <= 0")
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
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})\$", message = "Неверный номер телефона")
    var phoneNumber: String
)

enum class TypeOfPost { SALE, RENT }
enum class TypeOfEstate { LAND, HOUSE, FLAT, ROOM }
