package com.example.mymusic.radio.ui.viewmodel

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.radio.model.PopularPerson
import com.example.mymusic.radio.repository.PopularPersonRepository

class PopularPersonViewModel: BaseViewModel<PopularPerson, PopularPersonRepository>()  {

    init {
        repository = PopularPersonRepository()
    }
}