package com.example.mymusic.search.api

import com.example.mymusic.search.model.SearchByLyricsInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 根据歌词搜索
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 16:55
 **/

interface GetSearchByLyricsService {
    @GET("/cloudsearch?limit=100&type=1006")
    fun getSearchByLyricsInfo(@Query("keywords")word: String): Call<SearchByLyricsInfo>
}