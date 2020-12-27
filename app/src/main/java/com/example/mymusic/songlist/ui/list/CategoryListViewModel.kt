package com.example.mymusic.songlist.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.PageStatus
import com.example.mymusic.songlist.model.CategoryList
import com.example.mymusic.songlist.repository.CategoryListRepository


/**
 * @program: MyMusic
 *
 * @description: 分类列表viewmodel
 *
 * @author: YangRT
 *
 * @create: 2020-12-26 10:34
 **/

class CategoryListViewModel(val cat: String): BaseViewModel<CategoryList, CategoryListRepository>() {

    init {
        repository = CategoryListRepository(cat)
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

    class ViewModeFactory(private val cat: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoryListViewModel(cat) as T
        }
    }
}