package com.example.mymusic.singer.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GetSingerMusicInfoService {

    @GET("/artists")
    fun getSingerListInfo(@Query("id")id: Int)
}