package com.example.mymusic.play

import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.MyApplication
import com.example.mymusic.customview.lyrics.LyricsData
import com.example.mymusic.play.net.PlayApiImpl
import kotlinx.coroutines.launch

class PlayMusicViewModel: ViewModel(), LifecycleObserver {

    var isPlaying = MutableLiveData<Boolean>()
    var lyric = MutableLiveData<String>()

    fun getMusicLyrics(id: Long) {
        launch({
            val result = PlayApiImpl.getMusicLyricResponse(id)
            if (result.code != 200 || result.lrc.lyric.isEmpty()) {
                lyric.postValue("")
            } else {
                lyric.postValue(result.lrc.lyric)
                val t = LyricsData()

            }
        }, {
            Toast.makeText(MyApplication.context, "获取歌词失败！", Toast.LENGTH_SHORT).show()
        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}