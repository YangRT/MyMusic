package com.example.mymusic.find.api

import com.example.mymusic.find.model.RecommendSongListInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetRecommendSongListService {

    @GET("/personalized?limit=10")
    fun geRecommendSongListInfo(): Call<RecommendSongListInfo>
}