package com.example.mymusic.rank.ui

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.rank.model.RankInfo
import com.example.mymusic.rank.repository.AllRankRepository

class AllRankViewModel: BaseViewModel<RankInfo, AllRankRepository>() {

    init {
        repository = AllRankRepository()
    }
}