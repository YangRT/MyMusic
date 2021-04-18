package com.example.mymusic.utils

import android.content.Context
import android.text.TextUtils
import com.example.mymusic.MyApplication
import com.google.gson.Gson
import java.lang.reflect.Type

fun saveData(data:String,key:String){
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("data",data)
    editor.apply();
}

fun saveTime(time:Long,key:String){
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putLong("time",time)
    editor.apply();
}

fun saveUserInfo(userName:String){
    val sharedPreferences = MyApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("name",userName)
    editor.apply();
}

fun getSaveData(key: String):String?{
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    return sharedPreferences.getString("data",null)
}

fun getSaveTime(key: String):Long{
    val sharedPreferences = MyApplication.context.getSharedPreferences(key, Context.MODE_PRIVATE)
    return sharedPreferences.getLong("time",0)
}


fun getUserInfo():String?{
    val sharedPreferences = MyApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE)
    return sharedPreferences.getString("name",null)
}

fun  <T> getDataFromJson(key:String,type:Type):T?{
    val data = getSaveData(key)
    if(data != null){
        val result:T = Gson().fromJson(data,type)
        return result
    }
    return null
}

fun <T> toJson(data: T):String{
    val json = Gson().toJson(data)
    return json
}

fun getCookie(domain:String?):String? {
    val sp = MyApplication.context.getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE)
    if(!TextUtils.isEmpty(domain) && sp.contains(domain) && !TextUtils.isEmpty(sp.getString(domain,""))){
        return sp.getString(domain,"")
    }
    return null
}

fun saveCookies(domain: String, cookies:String){
    val sp = MyApplication.context.getSharedPreferences("cookies_prefs", Context.MODE_PRIVATE)
    val edit = sp.edit()
    if(!TextUtils.isEmpty(domain)){
        edit.putString(domain, cookies)
    }
    edit.apply()
}