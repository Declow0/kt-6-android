package ru.netology.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import ru.netology.model.Post

interface PostApi {
    @GET("api/v1/post")
    suspend fun getAllPosts(@Header("Authorization") token: String): Response<List<Post>>
}