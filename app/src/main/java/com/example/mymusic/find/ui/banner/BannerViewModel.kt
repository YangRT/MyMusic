package com.example.mymusic.find.ui.banner

import com.example.mymusic.find.model.BannerData
import com.example.mymusic.find.repository.BannerRepository
import com.example.wanandroid.base.BaseViewModel

class BannerViewModel: BaseViewModel<BannerData, BannerRepository>() {

    init {
        repository = BannerRepository()
    }
}