package com.example.mymusic.network

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.mymusic.MyApplication
import com.example.mymusic.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor: Interceptor {

    private val cookies_prf = "cookies_prefs";

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val cookie = com.example.mymusic.utils.getCookie(Constants.DOMAIN)
        if(cookie != null && !TextUtils.isEmpty(cookie) && request.url.toString().contains("/playlist/detail")){
            Log.e("MineFragmentAdd", cookie)
            builder.addHeader("Cookie",cookie!!)
        }
        return chain.proceed(builder.build())
    }

    private fun getCookie(url:String?, domain:String?):String?{
        val sp = MyApplication.context.getSharedPreferences(cookies_prf, Context.MODE_PRIVATE)
        if(!TextUtils.isEmpty(url) && sp.contains(url) && !TextUtils.isEmpty(sp.getString(url,""))){
            return sp.getString(url,"")
        }
        if(!TextUtils.isEmpty(domain) && sp.contains(domain) && !TextUtils.isEmpty(sp.getString(domain,""))){
            return sp.getString(domain,"")
        }
        return null
    }

}