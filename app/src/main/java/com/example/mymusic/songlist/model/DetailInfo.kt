package com.example.mymusic.songlist.model

import com.example.mymusic.base.model.Privilege

data class DetailInfo(val code: Int, val relatedVideos: String, val urls: String, val privileges: List<Privilege>, val playlist: String)

data class Playlist(val createTime: Long, val updateTime: Long,val coverImgUrl: String, val playCount: Int)