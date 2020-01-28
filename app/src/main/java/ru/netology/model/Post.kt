package ru.netology.model

import java.time.LocalDateTime

data class Post(
    val createdUser: String,
    val content: String,
    val createTime: LocalDateTime = LocalDateTime.now(),

    val favorite: Long = 0L,
    val share: Long = 0L,
    val comment: Long = 0L,

    val favoriteCurrentUser: Boolean = false,
    val shareCurrentUser: Boolean = false,
    val commentCurrentUser: Boolean = false,

    val address: String = "",
    val location: Pair<Double, Double>? = null
)