package com.example.mymusic.mine

import com.example.mymusic.base.model.DetailInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MineService {

    @GET("/user/account")
    fun getAccount(@Query("cookie")cookie: String): Call<GetAccountResponse>

    @GET("/login/status")
    fun getStatus(@Query("cookie")cookie: String): Call<GetLoginStatusResponse>

}