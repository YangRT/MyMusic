package com.example.mymusic.play

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
            val info = PlayedSongInfo(it.songId, it.songName, it.artist, it.duration, it.songCover)
            val dao = PlayedSongDatabase.instance.playedSongDao()
            dao.insert(info)
        }
        return songInfo
    }


}