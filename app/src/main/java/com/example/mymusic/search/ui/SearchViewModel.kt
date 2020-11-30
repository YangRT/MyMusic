package com.example.mymusic.search.ui

import com.example.mymusic.search.model.HotWord
import com.example.mymusic.search.repository.HotWordsRepository
import com.example.mymusic.base.BaseViewModel


/**
 * @program: MyMusic
 *
 * @description: 搜索 viewmodel
 *
 * @author: YangRT
 *
 * @create: 2020-11-25 22:57
 **/

class SearchViewModel: BaseViewModel<HotWord, HotWordsRepository>() {

    init {
        repository = HotWordsRepository()
    }
}