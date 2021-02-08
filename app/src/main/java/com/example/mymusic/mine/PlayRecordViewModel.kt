package com.example.mymusic.mine

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.mymusic.play.db.PlayedSongDatabase
import com.example.mymusic.play.db.PlayedSongInfo

class PlayRecordViewModel: ViewModel(), LifecycleObserver {

    fun getRecordList(): ArrayList<PlayedSongInfo> {
        return ArrayList(PlayedSongDatabase.instance.playedSongDao().queryAll())
    }
}