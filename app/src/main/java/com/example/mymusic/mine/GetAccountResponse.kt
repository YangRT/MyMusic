package com.example.mymusic.mine

import com.example.mymusic.base.model.Playlist
import com.example.mymusic.songlist.model.CategoryList

data class GetAccountResponse(val code: Int, val account: String?, val profile: String)

data class GetLoginStatusResponse(val data: StatusData)

data class StatusData(val code: Int, val account: Account, val profile: Profile)

data class Account(val userName: String, val userId: Long, val createTime: Long, val type: Int, val status: Int)

data class Profile(val nickname: String, val userId: Long, val avatarUrl: String, val backgroundUrl: String)

data class CollectedPlayListInfo(val code: Int, val playlist: List<CategoryList>, val more: Boolean)