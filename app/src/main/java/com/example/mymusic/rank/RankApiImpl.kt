package com.example.mymusic.rank

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.rank.api.GetAllRankInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RankApiImpl {

    private val getAllRankInfoService = ServiceCreator.create(GetAllRankInfoService::class.java)

    suspend fun getAllRankInfo() =
        withContext(Dispatchers.IO) { getAllRankInfoService.getAllRankInfo().await() }
}