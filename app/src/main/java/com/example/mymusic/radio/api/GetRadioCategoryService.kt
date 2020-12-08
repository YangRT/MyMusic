package com.example.mymusic.radio.api

import com.example.mymusic.radio.model.RadioCategoryInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetRadioCategoryService {

    @GET("/dj/catelist")
    fun getRadioCategoryInfo(): Call<RadioCategoryInfo>

}