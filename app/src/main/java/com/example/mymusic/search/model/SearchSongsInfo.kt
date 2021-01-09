package com.example.mymusic.search.model

import com.example.mymusic.base.model.Al
import com.example.mymusic.base.model.Ar


/**
 * @program: MyMusic
 *
 * @description: 搜索单曲结果
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 16:33
 **/

data class SearchSongsInfo(val code: Int, val needLogin: Boolean, val result: Result)

data class Result(val songCount: Int, val songs: List<SearchSong>)

data class SearchSong(val name: String, val id: Long, val ar: List<Ar>, val al: Al)