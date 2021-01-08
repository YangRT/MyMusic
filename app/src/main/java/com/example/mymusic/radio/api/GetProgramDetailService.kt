package com.example.mymusic.radio.api

import com.example.mymusic.radio.model.ProgramDetailInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @program: MyMusic
 *
 * @description: 节目详情
 *
 * @author: YangRT
 *
 * @create: 2021-01-08 21:41
 **/

interface GetProgramDetailService {

    @GET("/dj/program?limit=100")
    fun getProgramDetailInfo(@Query("rid")rid: Long): Call<ProgramDetailInfo>


}