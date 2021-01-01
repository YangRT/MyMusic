package com.example.mymusic.singer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.PageStatus
import com.example.mymusic.base.model.HotSong
import com.example.mymusic.singer.repository.SingerMusicRepository

class MusicViewModel(val id: Long): BaseViewModel<HotSong, SingerMusicRepository>() {

    init {
        repository = SingerMusicRepository(id)
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

    class ViewModeFactory(private val id: Long) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MusicViewModel(id) as T
        }
    }
}