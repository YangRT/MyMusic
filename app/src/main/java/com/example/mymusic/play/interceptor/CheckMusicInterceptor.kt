package com.example.mymusic.play.interceptor
import com.example.mymusic.play.event.CanNotPlayEvent
import com.example.mymusic.play.net.PlayApiImpl
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.intercept.AsyncInterceptor
import com.lzx.starrysky.intercept.InterceptorCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

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
                try {
                    val result = PlayApiImpl.getCheckMusicResponse(it.songId.toLong())
                    success = result.success
                    message = result.message
                    if (!success) {
                        message.let {
                            EventBus.getDefault().post(CanNotPlayEvent(message))
                        }
                        callback.onContinue(songInfo)
                    } else {
                        callback.onContinue(it)
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                    EventBus.getDefault().post(CanNotPlayEvent("网络错误"))
                    callback.onContinue(songInfo)
                }
            }
        }
    }
}