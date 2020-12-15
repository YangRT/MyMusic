package com.example.mymusic.singer.api

import com.example.mymusic.singer.model.SingerDetailInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSingerDetailInfoService {

    @GET("/artists/detail")
    fun getSingerDetailInfo(@Query("id")id: Long): Call<SingerDetailInfo>
}