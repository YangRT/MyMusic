package com.example.mymusic.find.ui.hotradio

import com.example.mymusic.find.model.HotRadio
import com.example.mymusic.find.repository.HotRadioRepository
import com.example.wanandroid.base.BaseViewModel

class HotRadioViewModel: BaseViewModel<HotRadio, HotRadioRepository>() {

    init {
        repository = HotRadioRepository()
    }
}