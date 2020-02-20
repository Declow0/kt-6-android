package ru.netology.repository

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import ru.netology.api.AuthApi
import ru.netology.api.retrofit.RetrofitClient
import ru.netology.model.ErrorApi
import ru.netology.model.User
import java.io.IOException

class AuthRepository {
    private val authApi: AuthApi by lazy {
        RetrofitClient.retrofit.create(AuthApi::class.java)
    }

    suspend fun authenticate(login: String, password: String) =
        authApi.authenticate(
            User(login, password)
        )

    suspend fun register(login: String, password: String) =
        authApi.register(
            User(login, password)
        )
}