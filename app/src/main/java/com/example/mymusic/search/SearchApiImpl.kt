package com.example.mymusic.search

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.search.api.*
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

    private val getSearchSingerService = ServiceCreator.create(GetSearchSingerService::class.java)

    private val getSearchRadioService = ServiceCreator.create(GetSearchRadioService::class.java)

    private val getSearchByLyricsService = ServiceCreator.create(GetSearchByLyricsService::class.java)

    private val getSearchSongListService = ServiceCreator.create(GetSearchSongListService::class.java)

    private val getSearchSongsService = ServiceCreator.create(GetSearchSongsService::class.java)

    suspend fun getHotWordsInfo() = withContext(Dispatchers.IO){ getHotWordsService.getHotWordsInfo().await() }

    suspend fun getSearchSingerInfo(word: String) = withContext(Dispatchers.IO){ getSearchSingerService.getSearchSingerInfo(word).await() }

    suspend fun getSearchRadioInfo(word: String) = withContext(Dispatchers.IO){ getSearchRadioService.getSearchRadioInfo(word).await() }

    suspend fun getSearchByLyricsInfo(word: String) = withContext(Dispatchers.IO){ getSearchByLyricsService.getSearchByLyricsInfo(word).await() }

    suspend fun getSearchSongListInfo(word: String) = withContext(Dispatchers.IO){ getSearchSongListService.getSearchSongListInfo(word).await() }

    suspend fun getSearchSongsInfo(word: String) = withContext(Dispatchers.IO){ getSearchSongsService.getSearchSongsInfo(word).await() }

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