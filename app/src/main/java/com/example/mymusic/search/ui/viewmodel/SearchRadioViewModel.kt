package com.example.mymusic.search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.PageStatus
import com.example.mymusic.radio.model.DjRadio
import com.example.mymusic.search.repository.SearchRadioRepository


/**
 * @program: MyMusic
 *
 * @description: 搜索播单viewmodel
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 18:01
 **/

class SearchRadioViewModel(var word: String): BaseViewModel<DjRadio, SearchRadioRepository>() {

    var isInit: Boolean = false

    init {
        repository = SearchRadioRepository(word)
    }

    class ViewModeFactory(private val word: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchRadioViewModel(word) as T
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