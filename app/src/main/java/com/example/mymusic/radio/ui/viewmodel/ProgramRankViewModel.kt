package com.example.mymusic.radio.ui.viewmodel

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.radio.model.TopList
import com.example.mymusic.radio.repository.ProgramRankRepository

class ProgramRankViewModel: BaseViewModel<TopList, ProgramRankRepository>()  {

    init {
        repository = ProgramRankRepository()
    }
}