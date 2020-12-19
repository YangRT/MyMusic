package com.example.mymusic.rank.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.Playlist
import com.example.mymusic.rank.RankApiImpl
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RankDetailRepository(val id: Long): BaseMvvmRepository<List<Playlist>>(false, "RankDetail $id", null)  {

    override suspend fun load(): BaseResult<List<Playlist>> {
        val rankDetailInfo = RankApiImpl.getRankDetailInfo(id)
        val result: BaseResult<List<Playlist>> = BaseResult()
        if(rankDetailInfo.code == 200){
            val resultList = ArrayList<Playlist>()
            resultList.add(rankDetailInfo.playlist)
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