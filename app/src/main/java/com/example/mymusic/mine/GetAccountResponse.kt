package com.example.mymusic.mine

data class GetAccountResponse(val code: Int, val account: String?, val profile: String)

data class GetLoginStatusResponse(val code: Int, val msg: String)