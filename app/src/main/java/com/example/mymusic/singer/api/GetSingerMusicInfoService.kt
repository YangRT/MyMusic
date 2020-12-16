package com.example.mymusic.singer.api

import com.example.mymusic.singer.model.SingerMusicInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSingerMusicInfoService {

    @GET("/artists")
    fun getSingerMusicInfo(@Query("id")id: Long): Call<SingerMusicInfo>
}