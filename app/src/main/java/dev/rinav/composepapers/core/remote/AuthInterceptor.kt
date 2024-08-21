package dev.rinav.composepapers.core.remote

import dev.rinav.composepapers.core.C
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .addHeader(name = "Authorization", value = "Client-ID ${C.API_KEY}")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}