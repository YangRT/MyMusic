package com.example.mymusic.play.net

data class PlayUrlResponse(val code: Int, val data: List<PlayUrlInfo>)

data class PlayUrlInfo(val id: Long, val url: String, val code: Int)

data class CheckMusicResponse(val success: Boolean, val message: String)