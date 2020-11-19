package com.example.wanandroid.base

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mymusic.utils.getDataFromJson
import com.example.mymusic.utils.saveData
import com.example.mymusic.utils.saveTime
import com.example.mymusic.utils.toJson
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * @program: WanAndroid
 *
 * @description: mvvm架构m层基类
 *
 * @author: YangRT
 *
 * @create: 2020-02-12 21:52
 **/

 abstract class BaseMvvmRepository<T>(var isPaging:Boolean,var key:String?,var data:String?) {

    private  val TAG = "BaseMvvmRepository"


    protected var pageNum = 0
    protected val mIsPaging = isPaging
    protected var mCachedPreferenceKey = key
    protected var mApkPreferenceData = data

    private var isFirst = true
    protected var isRefreshing = false


    protected fun saveDataToPreference(data:T){
        //保存数据
        val str = toJson(data)
        mCachedPreferenceKey?.let {
            saveData(str,it)
            saveTime(System.currentTimeMillis(),it)
        }

    }

    suspend fun getCacheData():BaseResult<T>{
        Log.e("BaseMvvmRepository","getCacheData")
        var result:BaseResult<T> = BaseResult()
        if(mCachedPreferenceKey != null){
            //获取缓存数据
            Log.e("BaseMvvmRepository", mCachedPreferenceKey!!)
            val data = getTClass()?.let {
                Log.e("BaseMvvmRepository",it.toString())
                getDataFromJson<T>(mCachedPreferenceKey!!, it)
            }
            result.data = data
            result.isFirst = true
            result.isEmpty = data==null
            result.isFromCache = true
            result.isPaging = isPaging
        }else{
            result.isFirst = true
            result.isEmpty = true
            result.isFromCache = true
            result.isPaging = isPaging
        }
        return result
    }

    suspend fun requestData():BaseResult<T>{
        Log.e("requestData",Thread.currentThread().name)
        return load()
    }

    abstract suspend fun load():BaseResult<T>


    abstract suspend fun refresh():BaseResult<T>

    open fun isNeedToUpdate():Boolean{
        return true
    }

    open fun getTClass():Type?{
        return null;
    }



}
