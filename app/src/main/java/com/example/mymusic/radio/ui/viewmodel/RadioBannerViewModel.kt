package com.example.mymusic.radio.ui.viewmodel

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.radio.model.RadioBanner
import com.example.mymusic.radio.repository.RadioBannerRepository

class RadioBannerViewModel: BaseViewModel<RadioBanner, RadioBannerRepository>() {

    init {
        repository = RadioBannerRepository()
    }
}