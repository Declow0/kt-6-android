package ru.netology.repository

import android.net.Uri
import ru.netology.model.Post
import ru.netology.model.x
import ru.netology.service.Period
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

val repository: PostRepository = PostRepository()

class PostRepository() {
    private val repository = HashMap<UUID, Post>()

    init {
        for (i in 1..1) {
            put(
                    Post(
                            "Netology Group Company",
                            "В чащах юга жил-был цитрус? Да, но фальшивый экземпляръ!",
                            LocalDateTime.now().minus(i * 10L, Period.MINUTES.chronoUnit),
                            2,
                            0,
                            10,
                            location = 55.7765289 x 37.6749378,
                            youtubeId = "WhWc3b3KhnY"
                    )
            )
        }

        for (i in 1..1) {
            put(
                    Post(
                            "JKH",
                            "Новое сообщение",
                            LocalDateTime.now().minus(i * 2L, Period.DAYS.chronoUnit),
                            0,
                            14,
                            10,
                            commentCurrentUser = true
                    )
            )
        }

        put(
                Post(
                        "Google",
                        "Используйте наш поиск",
                        LocalDateTime.now().minus(1L, Period.DAYS.chronoUnit),
                        50,
                        14,
                        6,
                        commercialContent = Uri.parse("https://google.com")
                )
        )

        val repost = Post(
                "RePoster",
                "Украл пост",
                LocalDateTime.now().minus(1L, Period.MINUTES.chronoUnit),
                0,
                14,
                10,
                original = repository.keys.first()
        )
        put(repost)

        put(
                Post(
                        "ReRePoster",
                        "Украл украденный пост",
                        LocalDateTime.now().minus(1L, Period.MINUTES.chronoUnit),
                        0,
                        14,
                        10,
                        original = repost.id
                )
        )
    }

    fun getList(): MutableList<Post> = repository.values.sortedByDescending { it.createTime }.toMutableList()

    fun put(post: Post): Post {
        repository[post.id] = post
        return post
    }

    fun get(uuid: UUID?): Post? {
        return repository[uuid]
    }
}