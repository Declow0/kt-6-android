package ru.netology.model

import android.net.Uri
import java.time.LocalDateTime
import java.util.*

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
        val location: Location? = null,

        val interactiveContent: Uri? = null,
        val id: UUID = UUID.randomUUID()
)