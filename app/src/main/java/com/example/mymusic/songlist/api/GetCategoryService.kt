package com.example.mymusic.songlist.api

import com.example.mymusic.songlist.model.SongListCategoryInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetCategoryService {

    @GET("/playlist/catlist")
    fun getSongListCategoryInfo(): Call<SongListCategoryInfo>
}