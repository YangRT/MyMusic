package com.example.mymusic.play.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetCheckMusicService {

    @GET("/song/url")
    fun getCheckMusicResponse(@Query("id")id: Long): Call<CheckMusicResponse>

}