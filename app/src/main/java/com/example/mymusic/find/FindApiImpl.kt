package com.example.mymusic.find

import com.example.mymusic.find.api.GetBannerService
import com.example.mymusic.find.api.GetRecommendSongListService
import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FindApiImpl {

    private val getBannerService: GetBannerService =
        ServiceCreator.create(GetBannerService::class.java)

    private val getRecommendSongListService: GetRecommendSongListService =
            ServiceCreator.create(GetRecommendSongListService::class.java)

    suspend fun getBanner() =
        withContext(Dispatchers.IO) { getBannerService.getBannerInfo().await() }

    suspend fun getRecommendSongList() =
            withContext(Dispatchers.IO) {
                getRecommendSongListService.geRecommendSongListInfo().await()
            }

    companion object {
        private var network: FindApiImpl? = null
        fun getInstance(): FindApiImpl {
            if (network == null) {
                synchronized(FindApiImpl::class.java) {
                    if (network == null) {
                        network = FindApiImpl()
                    }
                }
            }
            return network!!
        }
    }

}