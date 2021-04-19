package com.example.mymusic.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private val BASE_URL = "http://${BaseUrl.baseUrl}:3000"

    private val httpClient = OkHttpClient.Builder().addInterceptor(AddCookiesInterceptor())
    private val loginClient = OkHttpClient.Builder().addInterceptor(SaveCookiesInterceptor())

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())

    private val loginBuilder = Retrofit.Builder()
        .client(loginClient.build())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()

    private val loginRetrofit = loginBuilder.build()

    fun<T> createLogin(serviceClass:Class<T>):T = loginRetrofit.create(serviceClass)

    fun <T> create(serviceClass: Class<T>):T = retrofit.create(serviceClass)

}