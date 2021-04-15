package com.example.mymusic.customview.lyrics


/**
 * @program: MyMusic
 *
 * @description: 每句歌词 相关数据
 *
 * @author: YangRT
 *
 * @create: 2021-04-15 22:53
 **/

class LyricsData {

    private var lyrics: String = ""
    private var start: Long = -1L
    private var end: Long = -1L


    fun setLyrics(data: String) {
        lyrics = data
    }

    fun setStart(time: Long) {
        start = time
    }

    fun setEnd(time: Long) {
        end = time
    }

    fun getLyrics(): String {
        return lyrics
    }

    fun getStart(): Long {
        return start
    }

    fun getEnd(): Long {
        return end
    }



}