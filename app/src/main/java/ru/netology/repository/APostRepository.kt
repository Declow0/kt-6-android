package ru.netology.repository

import retrofit2.Response
import ru.netology.api.PostApi
import ru.netology.api.retrofit.RetrofitClient
import ru.netology.model.Post
import java.util.*

abstract class APostRepository(
    private val filter: (Post) -> Boolean
) {
    private val postApi: PostApi by lazy {
        RetrofitClient.retrofit.create(PostApi::class.java)
    }

    suspend fun getList(): Response<List<Post>> {
        return postApi.getAllPosts()
    }

    suspend fun get(uuid: UUID?): Response<Post> {
        return postApi.getPost(uuid.toString())
    }
}