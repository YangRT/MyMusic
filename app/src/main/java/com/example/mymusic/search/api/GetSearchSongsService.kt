package com.example.mymusic.search.api

import com.example.mymusic.search.model.SearchSongsInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 搜索单曲结果
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 16:26
 **/

interface GetSearchSongsService {

    @GET("/cloudsearch?limit=100")
    fun getSearchSongsInfo(@Query("keywords")word: String): Call<SearchSongsInfo>
}