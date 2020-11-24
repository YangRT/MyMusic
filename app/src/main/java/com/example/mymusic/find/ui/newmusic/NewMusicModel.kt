package com.example.mymusic.find.ui.newmusic

import androidx.lifecycle.ViewModel
import com.example.mymusic.find.model.BannerData
import com.example.mymusic.find.model.NewMusicData
import com.example.mymusic.find.repository.BannerRepository
import com.example.mymusic.find.repository.NewMusicListRepository
import com.example.wanandroid.base.BaseViewModel

class NewMusicModel: BaseViewModel<NewMusicData, NewMusicListRepository>() {

    init {
        repository = NewMusicListRepository()
    }
}