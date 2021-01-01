package com.example.mymusic.singer.ui.list

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.PageStatus
import com.example.mymusic.base.model.Artist
import com.example.mymusic.singer.repository.SingerListRepository

class SingerListViewModel: BaseViewModel<Artist, SingerListRepository>() {

    init {
        repository = SingerListRepository()
    }

    fun loadNextPage(){
        launch({
            if(!isFirst){
                var result = repository.loadNextPage()
                dealWithResult(result)
            }
        },{
            status.postValue(PageStatus.LOAD_MORE_FAILED)
        })
    }

    fun changeParam(type: Int, area: Int){
        launch({
            var result = repository.setParams(type, area)
            dealWithResult(result)
        },{
            status.postValue(PageStatus.NETWORK_ERROR)
        })
    }
}