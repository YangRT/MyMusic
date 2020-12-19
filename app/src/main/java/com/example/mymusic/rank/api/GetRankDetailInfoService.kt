package com.example.mymusic.rank.api

import com.example.mymusic.base.model.DetailInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetRankDetailInfoService {

    @GET("/playlist/detail")
    fun getRankDetailInfo(@Query("id")id: Long): Call<DetailInfo>

}