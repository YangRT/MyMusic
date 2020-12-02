package com.example.mymusic.singer

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.singer.api.GetSingerListInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SingerApiImpl {

    private val getSingerListInfoService =
        ServiceCreator.create(GetSingerListInfoService::class.java)

    suspend fun getSingerListInfo(page: Int, type: Int, area: Int) =
        withContext(Dispatchers.IO) { getSingerListInfoService.getSingerListInfo().await() }
}