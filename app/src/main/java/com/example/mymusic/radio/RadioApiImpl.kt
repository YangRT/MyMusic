package com.example.mymusic.radio

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.radio.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RadioApiImpl {

    private val getRadioCategoryService = ServiceCreator.create(GetRadioCategoryService::class.java)

    private val getRadioBannerService = ServiceCreator.create(GetRadioBannerService::class.java)

    private val getProgramRankService = ServiceCreator.create(GetProgramRankService::class.java)

    private val getPopularPersonService = ServiceCreator.create(GetPopularPersonService::class.java)

    private val getCategoryListService = ServiceCreator.create(GetCategoryListService::class.java)

    suspend fun getRadioCategoryInfo() =
        withContext(Dispatchers.IO) { getRadioCategoryService.getRadioCategoryInfo().await() }

    suspend fun getRadioBannerInfo() =
        withContext(Dispatchers.IO) { getRadioBannerService.getRadioBannerInfo().await() }

    suspend fun getProgramRankInfo() =
        withContext(Dispatchers.IO) { getProgramRankService.getRadioBannerInfo().await() }

    suspend fun getPopularPersonInfo() =
        withContext(Dispatchers.IO) { getPopularPersonService.getPopularPersonInfo().await() }

    suspend fun getCategoryListInfo(id: Int, page: Int) =
        withContext(Dispatchers.IO) { getCategoryListService.getCategoryListInfo(id, page).await() }
}