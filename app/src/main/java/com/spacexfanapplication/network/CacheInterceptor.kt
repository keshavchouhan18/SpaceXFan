package com.spacexfanapplication.network

import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!java.lang.Boolean.valueOf(request.header("APICache"))) {
            var builder = request.newBuilder().addHeader("Cache-Control", "no-cache")
                    .removeHeader("APICache").build()
            return chain.proceed(builder)
        }

        if (java.lang.Boolean.valueOf(request.header("ForceNetwork"))) {

            val builder = request.newBuilder().addHeader("Cache-Control", "no-cache").removeHeader("ForceNetwork")
                    .removeHeader("APICache").build()
            return chain.proceed(builder)
        }
        return chain.proceed(request)

    }
}