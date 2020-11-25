package com.example.mymusic.search

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.search.api.GetHotWordsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @program: MyMusic
 *
 * @description: 搜索相关请求类
 *
 * @author: YangRT
 *
 * @create: 2020-11-25 22:50
 **/

class SearchApiImpl {

    private val getHotWordsService = ServiceCreator.create(GetHotWordsService::class.java)

    suspend fun getHotWordsInfo() = withContext(Dispatchers.IO){ getHotWordsService.getHotWordsInfo().await() }

    companion object {
        private var network: SearchApiImpl? = null
        fun getInstance(): SearchApiImpl {
            if (network == null) {
                synchronized(SearchApiImpl::class.java) {
                    if (network == null) {
                        network = SearchApiImpl()
                    }
                }
            }
            return network!!
        }
    }
}