package com.example.mymusic.mine

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.mymusic.play.db.PlayedSongDatabase
import com.lzx.starrysky.SongInfo

class MineMusicViewModel: ViewModel(), LifecycleObserver {

    fun getRecordList(): ArrayList<SongInfo> {
        val list = ArrayList(PlayedSongDatabase.instance.playedSongDao().queryAll())
        val result = ArrayList<SongInfo>()
        for (item in list) {
            val info = SongInfo()
            info.songName = item.name
            info.artist = item.artist
            info.songId = item.id
            info.songUrl = item.url
            info.songCover = item.songCover
            info.duration = item.duration
            result.add(info)
        }
        return result
    }
}