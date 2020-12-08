package com.example.mymusic.rank.model

data class AllRankInfo(val code: Int,val artistToplist: ArtistTopList, val list: List<RankInfo>)

data class ArtistTopList(val name: String, val coverUrl: String, val upateFrequency: String, val position: Int)

data class RankInfo(val updateFrequency: String, val coverImgUrl: String,val createTime: Long,val description: String, val name: String, val id: Int)