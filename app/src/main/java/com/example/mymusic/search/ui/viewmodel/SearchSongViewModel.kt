package com.example.mymusic.search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.search.model.SearchSong
import com.example.mymusic.search.repository.SearchSongsRepository


/**
 * @program: MyMusic
 *
 * @description: 搜索单曲viewmodel
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 18:02
 **/

class SearchSongViewModel(val word: String): BaseViewModel<SearchSong, SearchSongsRepository>() {

    init {
        repository = SearchSongsRepository(word)
    }

    class ViewModeFactory(private val word: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchSongViewModel(word) as T
        }
    }
}