package com.example.mymusic.songlist.ui.category

import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.songlist.model.SongListSubCategory
import com.example.mymusic.songlist.repository.CategoryRepository

class CategoryViewModel: BaseViewModel<SongListSubCategory, CategoryRepository>() {

    init {
        repository = CategoryRepository()
    }
}