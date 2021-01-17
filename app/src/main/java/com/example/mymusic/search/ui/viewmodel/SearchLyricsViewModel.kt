package com.example.mymusic.search.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.PageStatus
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

class SearchLyricsViewModel(var word: String): BaseViewModel<SearchByLyricsSong, SearchByLyricsRepository>() {

    var isInit: Boolean = false

    init {
        repository = SearchByLyricsRepository(word)
    }

    class ViewModeFactory(private val word: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchLyricsViewModel(word) as T
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