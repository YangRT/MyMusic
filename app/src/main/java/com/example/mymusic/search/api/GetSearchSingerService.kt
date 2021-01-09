package com.example.mymusic.search.api

import com.example.mymusic.search.model.SearchSingerInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 搜索歌手结果
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 17:17
 **/

interface GetSearchSingerService {

    @GET("/cloudsearch?limit=100&type=100")
    fun getSearchSingerInfo(@Query("keywords")word: String): Call<SearchSingerInfo>
}