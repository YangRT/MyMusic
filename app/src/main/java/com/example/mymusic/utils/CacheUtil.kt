package com.example.mymusic.utils

import android.content.Context
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