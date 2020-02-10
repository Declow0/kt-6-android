package ru.netology.model

import android.net.Uri
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import ru.netology.deserializer.LocalDateTimeDeserializer
import ru.netology.deserializer.UriDeserializer
import java.time.LocalDateTime
import java.util.*

data class Post(
    val createdUser: String = "",

    val content: String = "",

    @JsonAdapter(LocalDateTimeDeserializer::class)
    val createTime: LocalDateTime = LocalDateTime.now(),

    val favorite: Long = 0L,
    val comment: Long = 0L,
    val share: Long = 0L,

    val favoriteByMe: Boolean = false,
    val shareByMe: Boolean = false,
    val commentByMe: Boolean = false,

    val address: String = "",
    val location: Location? = null,

    val youtubeId: String? = null,

    @JsonAdapter(UriDeserializer::class)
    val commercialContent: Uri? = null,
    val original: UUID? = null,
    val id: UUID = UUID.randomUUID(),
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