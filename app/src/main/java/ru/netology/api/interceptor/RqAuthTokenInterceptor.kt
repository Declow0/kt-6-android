package ru.netology.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class RqAuthTokenInterceptor(val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .header("Authorization", "Bearer $authToken")
                .build()
        )
}