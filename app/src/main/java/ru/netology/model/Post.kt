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

        val youtubeId: String? = null,
        val commercialContent: Uri? = null,
        val original: UUID? = null,
        val id: UUID = UUID.randomUUID(),
        val inner: Boolean = false
) {
    val type: Set<PostType>
        get() {
            val types = HashSet<PostType>()
            types.add(PostType.POST)

            if (youtubeId != null) types.add(PostType.YOUTUBE)
            if (location != null || address.isNotBlank()) types.add(PostType.GEO_EVENT)
            if (original != null) types.add(PostType.REPOST)
            if (commercialContent != null) types.add(PostType.COMMERCIAL)
            if (inner) types.add(PostType.INNER)

            return types
        }
}

enum class PostType {
    GEO_EVENT,
    REPOST,
    YOUTUBE,
    COMMERCIAL,
    INNER,
    POST
}