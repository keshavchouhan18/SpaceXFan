package com.spacexfanapplication.network.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder? = chain.request().newBuilder()
        requestBuilder?.addHeader(PLATFORM, ANDROID)
        requestBuilder?.addHeader(ACCEPT, APPJ)
        requestBuilder?.addHeader(VERSION_KEY, VERSION_VALUE)

        return chain.proceed(requestBuilder!!.build())
    }
}
