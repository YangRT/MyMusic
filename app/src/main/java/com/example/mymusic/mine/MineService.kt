package com.example.mymusic.mine

import retrofit2.Call
import retrofit2.http.GET

interface MineService {

    @GET("/user/account")
    fun getAccount(): Call<GetAccountResponse>

    @GET("/login/status")
    fun getStatus(): Call<GetLoginStatusResponse>

}