package ru.netology.model

import com.google.gson.annotations.JsonAdapter
import ru.netology.deserializer.LocalDateTimeJsonAdapter
import java.net.URL
import java.time.LocalDateTime
import java.util.*

data class Post(
    val createdUser: String = "",

    val content: String = "",

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val createTime: LocalDateTime = LocalDateTime.now(),

    val favorite: Long = 0L,
    val repost: Long = 0L,
    val share: Long = 0L,

    val favoriteByMe: Boolean = false,
    val shareByMe: Boolean = false,

    val address: String = "",
    val location: Location? = null,

    val youtubeId: String? = null,

    val commercialContent: URL? = null,

    val original: UUID? = null,
    val id: UUID = UUID.randomUUID(),
    val views: Long = 0L,

    @Transient
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