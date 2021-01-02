package com.example.mymusic.radio.api

import com.example.mymusic.radio.model.PopularPersonInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetPopularPersonService {

    @GET("dj/toplist/popular")
    fun getPopularPersonInfo(): Call<PopularPersonInfo>
}