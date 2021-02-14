package com.example.mymusic.login

data class LoginRegisterResponse(val code: Int, val msg: String, val message: String)

data class GetCaptchaResponse(val code: Int, val message: String)