package com.example.mymusic.find.api

import com.example.mymusic.find.model.NewMusicInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetNewMusicListInfoService {

    @GET("/personalized/newsong?limit=9")
    fun gettNewMusicListInfo(): Call<NewMusicInfo>
}