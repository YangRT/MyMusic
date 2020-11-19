package com.example.mymusic.search.api

import com.example.mymusic.search.model.SearchWordInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetHotWordsService {

    @GET("/search/hot")
    fun getBannerInfo(): Call<SearchWordInfo>
}