package com.example.mymusic.base.model


data class DetailInfo(val code: Int, val relatedVideos: String, val urls: String, val privileges: List<Privilege>, val playlist: Playlist)

data class Playlist(val id: Long, val createTime: Long, val updateTime: Long,val coverImgUrl: String, val playCount: Long,val shareCount: Int, val commentCount: Int, val subscribedCount: Int, val description: String, val name: String, val tracks: List<Track>)


data class Track(var name: String, var id: Long, var ar: List<Ar>, var fee: Int, var al: Al, var dt: Long, var publishTime: Long, var no: Int)


data class Al(var id: Long, var name: String, var picUrl: String)
