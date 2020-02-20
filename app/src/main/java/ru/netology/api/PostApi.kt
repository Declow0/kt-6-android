package ru.netology.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.netology.model.Post

interface PostApi {
    @GET("api/v1/post")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("api/v1/post/{id}")
    suspend fun getPost(@Path("id") id: String): Response<Post>
}