package ru.netology.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.netology.model.Token
import ru.netology.model.User

interface AuthApi {
    @POST("api/v1/authentication")
    suspend fun authenticate(@Body user: User): Response<Token>

    @POST("api/v1/registration")
    suspend fun register(@Body user: User): Response<Token>
}