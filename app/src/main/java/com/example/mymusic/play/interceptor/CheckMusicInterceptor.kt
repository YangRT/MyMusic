package com.example.mymusic.play.interceptor

import android.widget.Toast
import com.example.mymusic.MyApplication
import com.example.mymusic.play.event.CanNotPlayEvent
import com.example.mymusic.play.net.PlayApiImpl
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.intercept.AsyncInterceptor
import com.lzx.starrysky.intercept.InterceptorCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class CheckMusicInterceptor: AsyncInterceptor() {

    override fun getTag(): String {
        return "CheckMusic"
    }

    override fun process(songInfo: SongInfo?, callback: InterceptorCallback) {
        songInfo?.let {
            if (it.songUrl.isNotEmpty() && !it.songUrl.startsWith("http")) {
                callback.onContinue(it)
                return
            }
            var success = false
            var message = "无法播放"
            GlobalScope.launch {
                val result = PlayApiImpl.getCheckMusicResponse(it.songId.toLong())
                success = result.success
                message = result.message
            }
            if (!success) {
                EventBus.getDefault().post(CanNotPlayEvent(message))
                callback.onInterrupt(null)
            } else {
                callback.onContinue(it)
            }
        }
    }
}