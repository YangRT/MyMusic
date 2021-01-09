package com.example.mymusic.search.api

import com.example.mymusic.search.model.SearchRadioInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 搜索播单结果
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 17:26
 **/

interface GetSearchRadioService {

    @GET("/cloudsearch?limit=100&type=1009")
    fun getSearchRadioInfo(@Query("keywords")word: String): Call<SearchRadioInfo>
}