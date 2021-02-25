package com.example.mymusic.play.interceptor

import com.example.mymusic.play.db.DownloadSongInfo
import com.example.mymusic.play.db.PlayedSongDatabase
import com.example.mymusic.utils.DownLoadUtil
import com.lzx.starrysky.SongInfo
import okhttp3.Interceptor
import okhttp3.Response

class SaveDownloadSongInfoInterceptor(var info: SongInfo, var url: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.isSuccessful) {
            val downloadSongInfo = DownloadSongInfo(info.songId, info.songName, info.artist, info.duration, info.songCover, false, url)
            val dao = PlayedSongDatabase.instance.downloadSongDao()
            dao.insert(downloadSongInfo)
            DownLoadUtil.getInstance().refreshMedia(url)
        }
        return response
    }
}