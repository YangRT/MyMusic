package com.example.mymusic.search.model

import com.example.mymusic.base.model.Al
import com.example.mymusic.base.model.Ar


/**
 * @program: MyMusic
 *
 * @description: 按歌词搜索结果
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 16:58
 **/

data class SearchByLyricsInfo(val code: Int, val needLogin: Boolean, val result: SearchByLyricsResult)

data class SearchByLyricsResult(val songCount: Int, val songs: List<SearchByLyricsSong>)

data class SearchByLyricsSong(val name: String, val id: Long, val ar: List<Ar>, val al: Al, val lyrics: List<String>, val dt: Long)