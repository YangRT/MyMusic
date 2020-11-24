package com.example.mymusic.find.repository

import android.util.Log
import com.example.mymusic.find.FindApiImpl
import com.example.mymusic.find.model.NewMusicData
import com.example.wanandroid.base.BaseMvvmRepository
import com.example.wanandroid.base.BaseResult
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class NewMusicListRepository: BaseMvvmRepository<List<NewMusicData>>(false,"newMusicList",null) {

    override suspend fun load(): BaseResult<List<NewMusicData>> {
        val newMusicInfo = FindApiImpl.getInstance().getNewMusicList()
        Log.e("Main","$newMusicInfo")
        val result:BaseResult<List<NewMusicData>> = BaseResult()
        if(newMusicInfo.code == 200){
            val resultList = newMusicInfo.result
            result.isEmpty = resultList.isEmpty()
            result.isFirst = true
            result.isFromCache = false
            result.data = resultList
            result.isPaging = false
        }else{
            result.isEmpty = true
            result.isFirst = pageNum==0
            result.msg = "网络请求失败"
            result.isFromCache = false
            result.isPaging = false
        }
        if(result.isFirst){
            result.data?.let {
                saveDataToPreference(it)
            }
        }
        return result
    }

    override suspend fun refresh(): BaseResult<List<NewMusicData>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<NewMusicData>>(){}.type
    }
}