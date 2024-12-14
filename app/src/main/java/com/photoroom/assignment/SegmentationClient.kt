package com.photoroom.assignment

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SegmentationClient {

    private const val BASE_URL: String = "https://sdk.photoroom.com/"

    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val interceptor: HttpLoggingInterceptor by lazy {
        val tmp = HttpLoggingInterceptor()
        tmp.setLevel(HttpLoggingInterceptor.Level.BODY)
        tmp
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(BitmapConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val service: SegmentationService by lazy {
        retrofit.create(SegmentationService::class.java)
    }
}
