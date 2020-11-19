package com.example.mymusic.find.model

data class RecommendSongListInfo(val hasTaste:Boolean,val code:Int,val category:Int,val result:List<SongList>)
// trackCount 歌单中歌的个数
data class SongList(val id:Long,val type:Int,val name:String,val copywriter:String,val picUrl:String,val canDislike:Boolean,val trackNumberUpdateTime:Long,
                    val playCount:Int,val trackCount:Int,val highQuality:Boolean,val alg:String)