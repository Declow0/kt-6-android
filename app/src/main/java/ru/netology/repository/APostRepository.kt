package ru.netology.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.activity.BuildConfig
import ru.netology.api.PostApi
import ru.netology.model.Post
import java.util.*
import kotlin.collections.HashMap

abstract class APostRepository(
    private val filter: (Post) -> Boolean
) {
    private var isCached = false
    private val cachedPosts = HashMap<UUID, Post>()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val postApi: PostApi by lazy {
        retrofit.create(PostApi::class.java)
    }

    suspend fun getList(token: String): MutableList<Post> {
        if (!isCached) {
            cache(token)
        }

        return cachedPosts
            .values
            .sortedByDescending { it.createTime }
            .toMutableList()
    }

    fun get(uuid: UUID?): Post? {
        return cachedPosts[uuid]
    }

    private suspend fun cache(token: String) {
        cachedPosts.putAll(
            postApi.getAllPosts("Bearer $token")
                .body()!!
                .filter(filter)
                .map { it.id to it }
                .toMap()
        )
        isCached = true
    }
}