package ru.netology.lib

import com.google.gson.GsonBuilder
import ru.netology.model.Period
import ru.netology.model.Post
import ru.netology.model.x
import java.io.File
import java.net.URL
import java.time.LocalDateTime

object PostSerializer {
    val commercialList = listOf(
        Post(
            createdUser = "Google",
            content = "Используйте наш поиск!",
            favorite = 16,
            comment = 12,
            share = 41,
            shareByMe = true,
            commercialContent = URL("https://google.com")
        ),
        Post(
            createdUser = "Microsoft",
            content = "Печатать документы легко вместе с новым Office 365",
            favorite = 69,
            comment = 14,
            share = 21,
            favoriteByMe = true,
            commentByMe = true,
            shareByMe = true,
            commercialContent = URL("https://products.office.com")
        )
    )

    val postList = ArrayList<Post>().apply {
        val post = Post(
            createdUser = "Netology Group Company",
            content = "В чащах юга жил-был цитрус? Да, но фальшивый экземпляръ!",
            createTime = LocalDateTime.now().minus(10L, Period.MINUTES.chronoUnit),
            favorite = 15,
            comment = 16,
            share = 12,
            favoriteByMe = true,
            location = 55.7765289 x 37.6749378,
            youtubeId = "WhWc3b3KhnY"
        )
        add(post)
        add(
            Post(
                createdUser = "JKH",
                content = "Клёвое место, чтобы затусить!",
                createTime = LocalDateTime.now().minus(2L, Period.DAYS.chronoUnit),
                favorite = 14,
                comment = 10,
                commentByMe = true,
                address = "Красный пр-т., 22, Новосибирск, Новосибирская обл., 630007"
            )
        )

        val repost = Post(
            createdUser = "RePoster",
            content = "Украл пост",
            createTime = LocalDateTime.now().minus(1L, Period.MINUTES.chronoUnit),
            favorite = 14,
            comment = 10,
            original = post.id
        )
        add(repost)
        add(
            Post(
                createdUser = "ReRePoster",
                content = "Украл украденный пост",
                createTime = LocalDateTime.now().minus(1L, Period.MINUTES.chronoUnit),
                favorite = 0,
                comment = 14,
                share = 10,
                original = repost.id
            )
        )

        add(
            Post(
                createdUser = "RePoster",
                content = "Украл пост",
                createTime = LocalDateTime.now().minus(1L, Period.MINUTES.chronoUnit),
                favorite = 14,
                comment = 10,
                original = commercialList.first().id
            )
        )
    }
}

fun main() {
    val serializer = GsonBuilder()
        .setPrettyPrinting()
        .create()

    File("post.json").writeText(
        serializer.toJson(PostSerializer.postList)
    )

    File("commercial.json").writeText(
        serializer.toJson(PostSerializer.commercialList)
    )
}
