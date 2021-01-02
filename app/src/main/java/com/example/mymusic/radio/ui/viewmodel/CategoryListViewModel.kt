package com.example.mymusic.radio.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.PageStatus
import com.example.mymusic.radio.model.DjRadio
import com.example.mymusic.radio.repository.CategoryListRepository
import com.example.mymusic.rank.ui.RankDetailViewModel

class CategoryListViewModel(val id: Int): BaseViewModel<DjRadio, CategoryListRepository>() {

    init {
        repository = CategoryListRepository(id)
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

    class ViewModeFactory(private val id: Int) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoryListViewModel(id) as T
        }
    }

}