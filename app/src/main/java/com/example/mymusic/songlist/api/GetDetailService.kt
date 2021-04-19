package com.example.mymusic.songlist.api

import com.example.mymusic.base.model.DetailInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDetailService {

    @GET("/playlist/detail")
    fun getDetailInfo(@Query("id")id: Long,  @Query("timestamp")timestamp: Long): Call<DetailInfo>
}