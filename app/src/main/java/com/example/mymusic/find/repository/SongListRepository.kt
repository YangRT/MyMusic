package com.example.mymusic.find.repository

import android.util.Log
import com.example.mymusic.find.FindApiImpl
import com.example.mymusic.find.model.SongList
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SongListRepository: BaseMvvmRepository<List<SongList>>(false,"recommendSongList",null) {

    override suspend fun load(): BaseResult<List<SongList>> {
        val songListInfo = FindApiImpl.getInstance().getRecommendSongList()
        Log.e("Main","$songListInfo")
        val result: BaseResult<List<SongList>> = BaseResult()
        if(songListInfo.code == 200){
            val resultList = songListInfo.result
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

    override suspend fun refresh(): BaseResult<List<SongList>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<SongList>>(){}.type
    }

}