package com.example.mymusic.singer.api

import com.example.mymusic.singer.model.SingerListInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetSingerListInfoService {
    // page从0开始
    @GET("/artist/list?offset={page}&type={type}&area={type}&initial=-1")
    fun getSingerListInfo(): Call<SingerListInfo>
}