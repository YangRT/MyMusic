package com.example.mymusic.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {

    @GET("/login/cellphone")
    fun login(@Query("phone")phone: String, @Query("password")password: String): Call<LoginRegisterResponse>

}