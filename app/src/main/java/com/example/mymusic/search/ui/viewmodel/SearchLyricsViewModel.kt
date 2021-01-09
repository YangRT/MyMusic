package com.example.mymusic.search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.search.model.SearchByLyricsSong
import com.example.mymusic.search.repository.SearchByLyricsRepository


/**
 * @program: MyMusic
 *
 * @description: 搜索歌词viewmodel
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 18:03
 **/

class SearchLyricsViewModel(val word: String): BaseViewModel<SearchByLyricsSong, SearchByLyricsRepository>() {

    init {
        repository = SearchByLyricsRepository(word)
    }

    class ViewModeFactory(private val word: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchLyricsViewModel(word) as T
        }
    }
}