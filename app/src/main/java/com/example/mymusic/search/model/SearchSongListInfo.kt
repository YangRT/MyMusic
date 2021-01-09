package com.example.mymusic.search.model

import com.example.mymusic.base.model.Track


/**
 * @program: MyMusic
 *
 * @description: 搜索歌单info
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 16:46
 **/

data class SearchSongListInfo(val code: Int, val needLogin: Boolean, val result: SongListResult)

data class SongListResult(val playlistCount: Int, val playlists: List<SearchPlaylist>)

data class SearchPlaylist(val id: Long, val coverImgUrl: String, val playCount: Long, val trackCount: Int, val bookCount: Int, val subscribedCount: Int, val description: String, val name: String)
