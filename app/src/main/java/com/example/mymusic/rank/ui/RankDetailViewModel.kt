package com.example.mymusic.rank.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.model.Playlist
import com.example.mymusic.rank.repository.RankDetailRepository

class RankDetailViewModel(val id: Long): BaseViewModel<Playlist, RankDetailRepository>() {

    init {
        repository = RankDetailRepository(id)
    }

    class ViewModeFactory(private val id: Long) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RankDetailViewModel(id) as T
        }
    }
}