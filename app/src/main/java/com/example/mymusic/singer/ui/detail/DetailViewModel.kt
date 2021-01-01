package com.example.mymusic.singer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.model.Artist
import com.example.mymusic.singer.repository.SingerDetailRepository

class DetailViewModel(val id: Long): BaseViewModel<Artist, SingerDetailRepository>()  {

    init {
        repository = SingerDetailRepository(id)
    }

    class ViewModeFactory(private val id: Long) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailViewModel(id) as T
        }
    }
}