package com.spacexfanapplication.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(chain.request())

        if (java.lang.Boolean.valueOf(request.header("APICache"))) {
            return originalResponse.newBuilder()
                    .removeHeader("APICache")
                    .header(
                            "Pragma",
                            String.format("max-age=%d, only-if-cached, max-stale=%d", 600, 0)
                    )   //600 seconds caching
                    .header(
                            "Cache-Control",
                            String.format("max-age=%d, only-if-cached, max-stale=%d", 600, 0)
                    )   //600 seconds caching
                    .build()
        }

        if (java.lang.Boolean.valueOf(request.header("ForceNetwork"))) {
            return originalResponse.newBuilder()
                    .removeHeader("ForceNetwork")
                    .header(
                            "Pragma",
                            String.format("max-age=%d, only-if-cached, max-stale=%d", 600, 0)
                    )   //600 seconds caching
                    .header(
                            "Cache-Control",
                            String.format("max-age=%d, only-if-cached, max-stale=%d", 600, 0)
                    )   //600 seconds caching
                    .build()
        }

        return originalResponse
    }
}