package com.example.mymusic.songlist.api

import com.example.mymusic.songlist.model.CollectInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetCollectService {

    /*
        @param t 类型 1:收藏,2:取消收藏
        @param id : 歌单 id
     */
    @GET("/playlist/subscribe")
    fun getDetailInfo(@Query("t")type: Int, @Query("id")id: Long): Call<CollectInfo>

    // 用户收藏歌单列表： /user/playlist?uid=329530144
    // 歌单详情 subscribed 字段判断是否已经收藏
}