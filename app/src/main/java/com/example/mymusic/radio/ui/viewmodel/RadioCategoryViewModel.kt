package com.example.mymusic.radio.ui.viewmodel

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.radio.model.RadioCategory
import com.example.mymusic.radio.repository.RadioCategoryRepository

class RadioCategoryViewModel: BaseViewModel<RadioCategory, RadioCategoryRepository>() {

    init {
        repository = RadioCategoryRepository()
    }
}