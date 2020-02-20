package ru.netology.api.retrofit

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.activity.BuildConfig
import ru.netology.api.interceptor.RqAuthTokenInterceptor
import ru.netology.model.ErrorApi
import java.io.IOException

object RetrofitClient {
    private val okHttpClientBuilder = {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
    }

    var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                okHttpClientBuilder()
                    .build()
            )
            .build()
        private set

    fun addAuthToken(authToken: String) {
        retrofit = retrofit.newBuilder()
            .client(
                okHttpClientBuilder()
                    .addInterceptor(RqAuthTokenInterceptor(authToken))
                    .build()
            )
            .build()
    }

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