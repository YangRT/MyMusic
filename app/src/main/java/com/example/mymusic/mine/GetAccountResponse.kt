package com.example.mymusic.mine

data class GetAccountResponse(val code: Int, val account: String?, val profile: String)

data class GetLoginStatusResponse(val data: StatusData)

data class StatusData(val code: Int, val account: Account, val profile: Profile)

data class Account(val userName: String, val userId: Long, val createTime: Long, val type: Int, val status: Int)

data class Profile(val nickname: String, val userId: Long, val avatarUrl: String, val backgroundUrl: String)