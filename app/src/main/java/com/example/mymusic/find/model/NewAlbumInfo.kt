package com.example.mymusic.find.model

data class NewAlbumInfo(val code:Int, val albums: List<Album>)

data class Album(val name:String,val id:Int,val type:String,val size:Int,val picUrl:String,val publishTime:Long)