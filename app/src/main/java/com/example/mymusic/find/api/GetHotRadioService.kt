package com.example.mymusic.find.api

import com.example.mymusic.find.model.HotRadioInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetHotRadioService {

    @GET("/dj/hot")
    fun getHotRadioInfo(): Call<HotRadioInfo>

}