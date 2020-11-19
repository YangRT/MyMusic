package com.example.mymusic.find.ui.songlist

import com.example.mymusic.find.model.SongList
import com.example.mymusic.find.repository.SongListRepository
import com.example.wanandroid.base.BaseViewModel

class SongListViewModel: BaseViewModel<SongList, SongListRepository>() {

    init {
        repository = SongListRepository()
    }
}