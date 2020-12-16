package com.example.mymusic.singer.ui.detail

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.model.HotSong
import com.example.mymusic.singer.repository.SingerMusicRepository

class MusicViewModel(private val id: Long): BaseViewModel<HotSong, SingerMusicRepository>() {

    init {
        repository = SingerMusicRepository(id)
    }
}