package com.fazalulabid.samespacemusic.core.util

import android.content.Context
import com.fazalulabid.samespacemusic.core.util.NetworkUtils.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(
    private val context: Context
): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable(context)) {
            throw NoConnectionException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

class NoConnectionException : IOException() {
    override val message: String
        get() = super.message ?: "No Internet Connection"
}