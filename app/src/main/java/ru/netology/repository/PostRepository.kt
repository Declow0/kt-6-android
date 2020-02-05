package ru.netology.repository

import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.ContentType
import ru.netology.model.Post
import java.util.*
import kotlin.collections.HashMap

const val postUrl =
    "https://raw.githubusercontent.com/netology-code/bkt-code/master/coroutines/posts.json"
val repository: PostRepository = PostRepository()

class PostRepository {
    private var isInit = false
    private val cachedPosts = HashMap<UUID, Post>()

    suspend fun getList(): MutableList<Post> {
        if (!isInit) {
            init()
        }

        return cachedPosts
            .values
            .sortedByDescending { it.createTime }
            .toMutableList()
    }

    fun get(uuid: UUID?): Post? {
        return cachedPosts[uuid]
    }

    private suspend fun init() {
        cachedPosts.putAll(
            HttpClient {
                install(JsonFeature) {
                    acceptContentTypes = listOf(
                        ContentType.Text.Plain,
                        ContentType.Application.Json
                    )
                    serializer = GsonSerializer {
                        excludeFieldsWithoutExposeAnnotation()
                    }
                }
            }
                .get<List<Post>>(postUrl)
                // Workaround for initializing default values
                .map {
                    Post(
                        createdUser = it.createdUser,
                        content = it.content,
                        createTime = it.createTime,
                        favoriteCurrentUser = it.favoriteCurrentUser
                    )
                }
                .map { it.id to it }
                .toMap()
        )
        isInit = true
    }
}