package com.example.mymusic.find.model

import com.example.mymusic.base.model.Artist
import java.time.Duration

data class NewMusicInfo(val code: Int, val result: List<NewMusicData>)

data class NewMusicData(val type: Int, val picUrl: String,val song: Song)

data class Song(val name: String, val id: Long, val artists: List<Artist>, val duration: Long)