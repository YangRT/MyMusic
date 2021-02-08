package com.example.mymusic.play.interceptor

import com.example.mymusic.play.event.CanNotPlayEvent
import com.example.mymusic.play.net.PlayApiImpl
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.intercept.AsyncInterceptor
import com.lzx.starrysky.intercept.InterceptorCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class GetPlayUrlInterceptor : AsyncInterceptor() {
    override fun getTag(): String {
        return "GetPlayUrl"
    }

    override fun process(songInfo: SongInfo?, callback: InterceptorCallback) {
        songInfo?.let {
            if (it.songUrl.isNotEmpty() && !it.songUrl.startsWith("http")) {
                callback.onContinue(songInfo)
                return
            }
            GlobalScope.launch {
                val result = PlayApiImpl.getPlayUrlResponse(songInfo.songId.toLong())
                if (result.code == 200 && result.data.isNotEmpty() && result.data[0].code == 200) {
                    it.songUrl = result.data[0].url
                    callback.onContinue(it)
                } else {
                    EventBus.getDefault().post(CanNotPlayEvent("获取播放地址错误"))
                    callback.onInterrupt(null)
                }
            }
        }
    }
}