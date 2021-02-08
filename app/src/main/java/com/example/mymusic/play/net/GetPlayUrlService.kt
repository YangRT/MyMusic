package com.example.mymusic.play.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetPlayUrlService {

    @GET("/song/url")
    fun getPlayUrl(@Query("id")id: Long): Call<PlayUrlResponse>
}