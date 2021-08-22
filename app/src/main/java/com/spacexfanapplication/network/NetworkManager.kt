package com.spacexfanapplication.network

import androidx.databinding.library.BuildConfig
import com.google.gson.GsonBuilder
import com.spacexfanapplication.FlavourConstant
import com.spacexfanapplication.SpaceXApplication
import com.spacexfanapplication.network.api.ApiInterceptor
import com.spacexfanapplication.network.api.LiveDataCallAdapterFactory
import com.spacexfanapplication.network.interceptor.ResponseInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    private const val CACHE_SIZE = 10 * 1024 * 1024

    val webService: WebService
        get() = getNetworkWebService()

    lateinit var retrofit: Retrofit
        private set

    private fun getNetworkWebService(): WebService {
        val cache = Cache(SpaceXApplication.context.cacheDir, CACHE_SIZE.toLong())
        val cachingInterceptor = CacheInterceptor()
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(90, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        // to enable logging in debug mode
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor(ApiInterceptor())
        builder.cache(cache)
        builder.addInterceptor(cachingInterceptor)
        builder.addNetworkInterceptor(ResponseInterceptor())
        val client = builder.build()
        retrofit = getRetrofit(client)
        return retrofit.create(WebService::class.java)
    }

    private fun getRetrofit(client: OkHttpClient?): Retrofit {

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(FlavourConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))

        if (client != null) {
            retrofit.client(client)
        }

        return retrofit.build()
    }
}