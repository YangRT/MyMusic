package com.example.mymusic.songlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.model.Playlist
import com.example.mymusic.songlist.repository.DetailRepository

class DetailViewModel(val id: Long) : BaseViewModel<Playlist, DetailRepository>() {

    init {
        repository = DetailRepository(id)
    }

    class ViewModeFactory(private val id: Long) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailViewModel(id) as T
        }
    }
}