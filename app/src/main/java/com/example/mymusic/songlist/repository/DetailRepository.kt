package com.example.mymusic.songlist.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.Playlist
import com.example.mymusic.songlist.SongListApiImpl
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DetailRepository(val id: Long): BaseMvvmRepository<List<Playlist>>(false, "SongListDetail $id", null) {

    override suspend fun load(): BaseResult<List<Playlist>> {
        val time = System.currentTimeMillis()
        val detailInfo = SongListApiImpl.getDetailInfo(id, time)
        val result: BaseResult<List<Playlist>> = BaseResult()
        if(detailInfo.code == 200){
            val resultList = ArrayList<Playlist>()
            resultList.add(detailInfo.playlist)
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

    override suspend fun refresh(): BaseResult<List<Playlist>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<Playlist>>() {}.type
    }

}