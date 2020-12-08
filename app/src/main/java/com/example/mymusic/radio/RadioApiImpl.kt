package com.example.mymusic.radio

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.radio.api.GetRadioCategoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RadioApiImpl {

    private val getRadioCategoryService = ServiceCreator.create(GetRadioCategoryService::class.java)

    suspend fun getRadioCategoryInfo() =
        withContext(Dispatchers.IO) { getRadioCategoryService.getRadioCategoryInfo().await() }
}