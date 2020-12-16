package com.example.mymusic.radio.api

import com.example.mymusic.radio.model.ProgramRankInfo
import retrofit2.Call
import retrofit2.http.GET

interface GetProgramRankService {

    @GET("dj/program/toplist?limit=5")
    fun getRadioBannerInfo(): Call<ProgramRankInfo>

}