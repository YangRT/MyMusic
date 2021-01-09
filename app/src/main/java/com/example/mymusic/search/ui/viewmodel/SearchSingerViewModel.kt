package com.example.mymusic.search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.search.model.SearchArtist
import com.example.mymusic.search.repository.SearchSingerRepository


/**
 * @program: MyMusic
 *
 * @description: 搜索歌手viewmodel
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 18:01
 **/

class SearchSingerViewModel(val word: String): BaseViewModel<SearchArtist, SearchSingerRepository>() {

    init {
        repository = SearchSingerRepository(word)
    }

    class ViewModeFactory(private val word: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchSingerViewModel(word) as T
        }
    }
}