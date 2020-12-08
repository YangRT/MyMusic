package com.example.mymusic.rank.api

import com.example.mymusic.rank.model.AllRankInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetAllRankInfoService {

    @GET("/toplist")
    fun getAllRankInfo(): Call<AllRankInfo>
}