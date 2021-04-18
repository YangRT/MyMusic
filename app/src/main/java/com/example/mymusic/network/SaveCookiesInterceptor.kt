package com.example.mymusic.network

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.mymusic.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookiesInterceptor : Interceptor {

    val COOKIE_PRF = "cookies_prefs";

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if(!response.headers("Set-Cookie").isEmpty()){
            val cookies = response.headers("Set-Cookie")
            val cookie = encodeCookie(cookies)
            saveCookies(request.url.toString(),request.url.host,cookie)
            Log.e("SaveInterceptor", "${request.url.host}")
        }
        return response
    }

    private fun encodeCookie(cookies: List<String>):String{
        val sb = StringBuilder()

        val set :MutableSet<String> = HashSet<String>()
        for(cookie in cookies){
            val arr = cookie.split(";")
            for(s in arr){
                if(set.contains(s)) {
                    continue
                }
                Log.e("cookies:",s)
                set.add(s)
            }
        }
        val iterator = set.iterator()
        while(iterator.hasNext()){
            val cookie:String = iterator.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if(sb.length - 1 ==last){
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    fun saveCookies(url:String?,domain:String?,cookies:String){
        Log.e("MineFragmentSave", cookies)
        val sp = MyApplication.context.getSharedPreferences(COOKIE_PRF, Context.MODE_PRIVATE)
        val edit = sp.edit()
        if(!TextUtils.isEmpty(url)){
            edit.putString(url,cookies)
        }
        if(!TextUtils.isEmpty(domain)){
            edit.putString(domain,cookies)
        }
        edit.apply()
    }

}