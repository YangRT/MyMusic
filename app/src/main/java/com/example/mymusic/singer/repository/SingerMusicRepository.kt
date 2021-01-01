package com.example.mymusic.singer.repository

import android.util.Log
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.HotSong
import com.example.mymusic.singer.SingerApiImpl
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SingerMusicRepository(private val id: Long): BaseMvvmRepository<List<HotSong>>(true, "singerMusic${id}", null) {

    override suspend fun load(): BaseResult<List<HotSong>> {
        val musicInfo = SingerApiImpl.getSingerMusicInfo(pageNum * 50,id)
        val result: BaseResult<List<HotSong>> = BaseResult()
        if(musicInfo.code == 200){
            pageNum = if (isRefreshing) {
                1
            } else {
                pageNum + 1
            }
            val resultList = musicInfo.songs
            result.isEmpty = resultList.isEmpty()
            result.isFirst = pageNum == 1
            result.isFromCache = false
            result.data = resultList
            result.isPaging = true
        }else{
            result.isEmpty = true
            result.isFirst = pageNum==0
            result.msg = "网络请求失败"
            result.isFromCache = false
            result.isPaging = true
        }
        if(result.isFirst){
            result.data?.let {
                saveDataToPreference(it)
            }
        }
        return result
    }

    override suspend fun refresh(): BaseResult<List<HotSong>> {
        pageNum = 0
        isRefreshing = true
        return load()
    }

    suspend fun loadNextPage(): BaseResult<List<HotSong>> {
        isRefreshing = false
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<HotSong>>() {}.type
    }
}