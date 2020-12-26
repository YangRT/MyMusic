package com.example.mymusic.songlist

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.songlist.api.GetCategoryListService
import com.example.mymusic.songlist.api.GetCategoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SongListApiImpl {

    private val getCategoryService = ServiceCreator.create(GetCategoryService::class.java)
    private val getCategoryListService = ServiceCreator.create(GetCategoryListService::class.java)

    suspend fun getCategoryInfo() =
        withContext(Dispatchers.IO) { getCategoryService.getSongListCategoryInfo().await() }

    suspend fun getCategoryListInfo(page: Int, cat: String) =
        withContext(Dispatchers.IO) { getCategoryListService.getCategoryListInfo(page, cat).await()}
}