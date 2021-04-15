package com.example.mymusic.play.net


/**
 * @program: MyMusic
 *
 * @description: 获取歌词response
 *
 * @author: YangRT
 *
 * @create: 2021-04-15 22:32
 **/

data class MusicLyricsResponse(val code: Int, val sgc: Boolean, val sfc: Boolean, val qfy: Boolean, val lrc: Lyric, val klyric: Lyric, val tlyric: Lyric)

data class Lyric(val version: Int, val lyric: String)