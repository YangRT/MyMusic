package com.example.mymusic

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.mymusic.play.interceptor.CheckMusicInterceptor
import com.example.mymusic.play.interceptor.GetPlayUrlInterceptor
import com.example.mymusic.play.interceptor.SavePlayInfoInterceptor
import com.lzx.starrysky.StarrySky

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("MyApplication","onCreate")
        context = applicationContext
        StarrySky.init(this)
            .addInterceptor(CheckMusicInterceptor())
            .addInterceptor(GetPlayUrlInterceptor())
            .addInterceptor(SavePlayInfoInterceptor())
            .apply()
    }
    companion object {
        lateinit var context: Context
    }

    fun getContext():Context{
        return context
    }

}