package com.example.mymusic.search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.PageStatus
import com.example.mymusic.search.model.SearchPlaylist
import com.example.mymusic.search.repository.SearchSongListRepository


/**
 * @program: MyMusic
 *
 * @description: 搜索歌单viewm
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 18:02
 **/

class SearchSongListViewModel(var word: String): BaseViewModel<SearchPlaylist, SearchSongListRepository>() {

    var isInit: Boolean = false

    init {
        repository = SearchSongListRepository(word)
    }

    class ViewModeFactory(private val word: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchSongListViewModel(word) as T
        }
    }

    fun search() {
        launch(
            {
                var result = repository.search(word)
                isFirst = false
                dealWithResult(result)
            },{
                if (data.value?.size == 0){
                    status.postValue(PageStatus.NETWORK_ERROR)
                }
            }
        )
    }
}