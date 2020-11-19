package com.example.mymusic

import android.app.Application
import android.content.Context
import android.util.Log

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("MyApplication","onCreate")
        context = applicationContext
    }
    companion object {
        lateinit var context: Context
    }

    fun getContext():Context{
        return context
    }

}