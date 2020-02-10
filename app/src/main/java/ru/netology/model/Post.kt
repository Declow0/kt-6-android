package ru.netology.model

import android.net.Uri
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import ru.netology.deserializer.LocalDateTimeDeserializer
import ru.netology.deserializer.UriDeserializer
import java.time.LocalDateTime
import java.util.*

data class Post(
    @Expose
    val createdUser: String = "",

    @Expose
    val content: String = "",

    @JsonAdapter(LocalDateTimeDeserializer::class)
    @Expose
    val createTime: LocalDateTime = LocalDateTime.now(),

    @Expose
    val favorite: Long = 0L,
    @Expose
    val comment: Long = 0L,
    @Expose
    val share: Long = 0L,

    @SerializedName("likedByMe")
    @Expose
    val favoriteByMe: Boolean = false,
    @Expose
    val shareByMe: Boolean = false,
    @Expose
    val commentByMe: Boolean = false,

    @Expose
    val address: String = "",
    @Expose
    val location: Location? = null,

    @Expose
    val youtubeId: String? = null,

    @JsonAdapter(UriDeserializer::class)
    @Expose
    val commercialContent: Uri? = null,
    @Expose
    val original: UUID? = null,
    @Expose
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