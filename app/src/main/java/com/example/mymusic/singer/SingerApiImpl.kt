package com.example.mymusic.singer

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.singer.api.GetSingerDetailInfoService
import com.example.mymusic.singer.api.GetSingerListInfoService
import com.example.mymusic.singer.api.GetSingerMusicInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SingerApiImpl {

    private val getSingerListInfoService =
        ServiceCreator.create(GetSingerListInfoService::class.java)

    private val getSingerMusicInfoService =
        ServiceCreator.create(GetSingerMusicInfoService::class.java)

    private val getSingerDetailInfoService =
        ServiceCreator.create(GetSingerDetailInfoService::class.java)

    suspend fun getSingerListInfo(page: Int, type: Int, area: Int) =
        withContext(Dispatchers.IO) { getSingerListInfoService.getSingerListInfo(page,type,area,-1).await() }

    suspend fun getSingerMusicInfo(id: Long) =
        withContext(Dispatchers.IO) { getSingerMusicInfoService.getSingerMusicInfo(id).await() }

    suspend fun getSingerDetailInfo(id: Long) =
        withContext(Dispatchers.IO) { getSingerDetailInfoService.getSingerDetailInfo(id).await() }
}