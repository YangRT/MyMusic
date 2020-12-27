package com.example.mymusic.songlist.api

import com.example.mymusic.songlist.model.CategoryListInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 分类歌单列表
 *
 * @author: YangRT
 *
 * @create: 2020-12-26 10:02
 **/

interface GetCategoryListService {

    @GET("/top/playlist")
    fun getCategoryListInfo(@Query("offset")offset: Int, @Query("cat") type:String): Call<CategoryListInfo>
}