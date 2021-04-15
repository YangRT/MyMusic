package com.example.mymusic.play.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 获取歌词
 *
 * @author: YangRT
 *
 * @create: 2021-04-15 22:26
 **/

interface GetLyricsService {

    @GET("/lyric")
    fun getPlayUrl(@Query("id")id: Long): Call<MusicLyricsResponse>
}