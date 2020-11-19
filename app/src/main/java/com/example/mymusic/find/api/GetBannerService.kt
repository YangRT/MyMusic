package com.example.mymusic.find.api

import com.example.mymusic.find.model.BannerInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetBannerService {

    @GET("/banner")
    fun getBannerInfo(): Call<BannerInfo>
}