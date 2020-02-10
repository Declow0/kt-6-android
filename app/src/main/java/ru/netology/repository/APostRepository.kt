package ru.netology.repository

import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.ContentType
import ru.netology.model.Post
import java.util.*
import kotlin.collections.HashMap

abstract class APostRepository(
    private val contentUrl: String
) {
    private var isCached = false
    private val cachedPosts = HashMap<UUID, ru.netology.model.Post>()

    suspend fun getList(): MutableList<ru.netology.model.Post> {
        if (!isCached) {
            cache()
        }

        return cachedPosts
            .values
            .sortedByDescending { it.createTime }
            .toMutableList()
    }

    fun get(uuid: UUID?): ru.netology.model.Post? {
        return cachedPosts[uuid]
    }

    private suspend fun cache() {
        cachedPosts.putAll(
            HttpClient {
                install(JsonFeature) {
                    acceptContentTypes = listOf(
                        ContentType.Text.Plain,
                        ContentType.Application.Json
                    )
                    serializer = GsonSerializer()
                }
            }
                .get<List<ru.netology.model.Post>>(contentUrl)
                .map { it.id to it }
                .toMap()
        )
        isCached = true
    }
}