package com.example.mymusic

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.mymusic.play.interceptor.CheckMusicInterceptor
import com.example.mymusic.play.interceptor.GetPlayUrlInterceptor
import com.example.mymusic.play.interceptor.SavePlayInfoInterceptor
import com.iflytek.cloud.*
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.notification.INotification

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("MyApplication","onCreate")
        context = applicationContext
        StarrySky.init(this)
            .setNotificationSwitch(true)
            .setNotificationType(INotification.SYSTEM_NOTIFICATION)
            .addInterceptor(CheckMusicInterceptor())
            .addInterceptor(GetPlayUrlInterceptor())
            .addInterceptor(SavePlayInfoInterceptor())
            .apply()
        SpeechUtility.createUtility(context, SpeechConstant.APPID +"="+R.string.app_id)
    }
    companion object {
        lateinit var context: Context
    }

    fun getContext():Context{
        return context
    }

}