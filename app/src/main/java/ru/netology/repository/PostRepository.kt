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
        put(
                Post(
                        "Netology Group Company",
                        "В чащах юга жил-был цитрус? Да, но фальшивый экземпляръ!",
                        LocalDateTime.now().minus(2L, Period.DAYS.chronoUnit),
                        2,
                        0,
                        10,
                        location = 55.7765289 x 37.6749378,
                        interactiveContent = Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY")
                )
        )

        put(
                Post(
                        "JKH",
                        "В чащах юга жил-был цитрус? Да, но фальшивый экземпляръ!",
                        LocalDateTime.now().minus(2L, Period.DAYS.chronoUnit),
                        0,
                        14,
                        10,
                        commentCurrentUser = true
                )
        )
    }

    fun getList(): MutableList<Post> = repository.values.toMutableList()

    fun put(post: Post): Post {
        repository[post.id] = post
        return post
    }
}