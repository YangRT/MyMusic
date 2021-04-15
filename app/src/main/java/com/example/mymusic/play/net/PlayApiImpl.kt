package com.example.mymusic.play.net

import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PlayApiImpl {

    private val getPlayUrlService = ServiceCreator.create(GetPlayUrlService::class.java)

    private val getCheckMusicService = ServiceCreator.create(GetCheckMusicService::class.java)

    private val getLyricService = ServiceCreator.create(GetLyricsService::class.java)

    suspend fun getPlayUrlResponse(id: Long) = withContext(Dispatchers.IO) {
        getPlayUrlService.getPlayUrl(id).await()
    }

    suspend fun getCheckMusicResponse(id: Long) = withContext(Dispatchers.IO) {
        getCheckMusicService.getCheckMusicResponse(id).await()
    }

    suspend fun getMusicLyricResponse(id: Long) = withContext(Dispatchers.IO) {
        getLyricService.getPlayUrl(id).await()
    }
}