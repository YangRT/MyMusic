package com.example.mymusic.play.interceptor

import com.example.mymusic.play.db.PlayedSongDatabase
import com.example.mymusic.play.db.PlayedSongInfo
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.intercept.SyncInterceptor

class SavePlayInfoInterceptor : SyncInterceptor {

    override fun getTag(): String {
        return "SavePlayInfoInterceptor"
    }

    override fun process(songInfo: SongInfo?): SongInfo? {
        songInfo?.let {
            if (it.songUrl.isEmpty()) {
                it.songUrl = "empty"
                return it
            }
            val info = if (it.songUrl.startsWith("http")) {
                PlayedSongInfo(it.songId, it.songName, it.artist, it.duration, it.songCover, false, it.songUrl)
            } else {
                PlayedSongInfo(it.songId, it.songName, it.artist, it.duration, it.songCover, true, it.songUrl)
            }
            val dao = PlayedSongDatabase.instance.playedSongDao()
            dao.insert(info)
        }
        return songInfo
    }


}