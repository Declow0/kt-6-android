package ru.netology.repository

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.activity.BuildConfig
import ru.netology.api.AuthApi
import ru.netology.model.ErrorApi
import ru.netology.model.User
import java.io.IOException

object AuthRepository {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    suspend fun authenticate(login: String, password: String) =
        authApi.authenticate(
            User(login, password)
        )

    suspend fun register(login: String, password: String) =
        authApi.register(
            User(login, password)
        )

    fun parseError(response: Response<*>): ErrorApi {
        val converter: Converter<ResponseBody, ErrorApi> = retrofit
            .responseBodyConverter(ErrorApi::class.java, arrayOfNulls<Annotation>(0))
        val error: ErrorApi
        error = try {
            converter.convert(response.errorBody()!!)!!
        } catch (e: IOException) {
            return ErrorApi("")
        }
        return error
    }
}