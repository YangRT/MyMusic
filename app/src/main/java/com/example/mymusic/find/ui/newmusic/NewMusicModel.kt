package com.example.mymusic.find.ui.newmusic

import com.example.mymusic.find.model.NewMusicData
import com.example.mymusic.find.repository.NewMusicListRepository
import com.example.mymusic.base.BaseViewModel

class NewMusicModel: BaseViewModel<NewMusicData, NewMusicListRepository>() {

    init {
        repository = NewMusicListRepository()
    }
}