package com.example.mymusic.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RegisterService {

    @GET("/register/cellphone")
    fun register(@Query("phone")phone: String, @Query("password")password: String, @Query("captcha")captcha: String, @Query("nickname")nickname: String): Call<LoginRegisterResponse>

    @GET("/captcha/sent")
    fun getCaptcha(@Query("phone")phone: String): Call<GetCaptchaResponse>
}