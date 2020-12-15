package com.example.mymusic.singer.model

import com.example.mymusic.base.model.Artist
import com.example.mymusic.base.model.HotSong

class SingerMusicInfo(val code: Int, val more: Boolean, val artist: Artist, val hotSongs: List<HotSong>)