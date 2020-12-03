package com.example.mymusic.singer.api

import com.example.mymusic.singer.model.SingerListInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSingerListInfoService {
    // page从0开始
    @GET("/artist/list")
    fun getSingerListInfo(@Query("offset")offset: Int,@Query("type") type:Int,@Query("area") area:Int,@Query("initial") initial:Int): Call<SingerListInfo>
}