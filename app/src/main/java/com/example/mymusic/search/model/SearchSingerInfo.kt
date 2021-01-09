package com.example.mymusic.search.model

/**
 * @program: MyMusic
 *
 * @description: 搜索歌手info
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 17:19
 **/

data class SearchSingerInfo(val code: Int, val needLogin: Boolean, val result: SearchSingerResult)

data class SearchSingerResult(val artistCount: Int, val artists: List<SearchArtist>)

data class SearchArtist(val name: String, val id: Long, val picUrl: String)