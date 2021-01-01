package com.example.mymusic.singer.api

import com.example.mymusic.singer.model.SingerMusicInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSingerMusicInfoService {

    @GET("/artist/songs")
    fun getSingerMusicInfo(@Query("offset")offset: Int, @Query("id")id: Long): Call<SingerMusicInfo>
}