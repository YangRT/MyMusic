package com.example.mymusic.radio.api

import com.example.mymusic.radio.model.RadioBannerInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetRadioBannerService {

    @GET("/dj/banner")
    fun getRadioBannerInfo(): Call<RadioBannerInfo>
}