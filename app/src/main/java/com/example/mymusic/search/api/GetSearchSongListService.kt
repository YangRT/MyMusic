package com.example.mymusic.search.api

import com.example.mymusic.search.model.SearchSongListInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 搜索歌单结果
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 16:43
 **/

interface GetSearchSongListService {

    @GET("/cloudsearch?limit=100&type=1000")
    fun getSearchSongListInfo(@Query("keywords")word: String): Call<SearchSongListInfo>
}