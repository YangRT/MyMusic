package com.example.mymusic.radio.api

import com.example.mymusic.radio.model.ProgramRankInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetProgramRankService {

    @GET("dj/program/toplist")
    fun getRadioBannerInfo(): Call<ProgramRankInfo>

}