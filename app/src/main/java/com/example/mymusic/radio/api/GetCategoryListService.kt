package com.example.mymusic.radio.api

import com.example.mymusic.radio.model.CategoryListInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetCategoryListService {

    @GET("/dj/radio/hot?limit=50")
    fun getCategoryListInfo(@Query("cateId")id: Int, @Query("offset")offset: Int): Call<CategoryListInfo>
}