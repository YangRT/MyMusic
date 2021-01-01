package com.example.mymusic.singer.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.Artist
import com.example.mymusic.singer.SingerApiImpl
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SingerDetailRepository(val id: Long): BaseMvvmRepository<List<Artist>>(false, "SingerDetail${id}", null) {

    override suspend fun load(): BaseResult<List<Artist>> {
        val singerDetailInfo = SingerApiImpl.getSingerDetailInfo(id)
        val result: BaseResult<List<Artist>> = BaseResult()
        if(singerDetailInfo.code == 200){
            val resultList = ArrayList<Artist>()
            resultList.add(singerDetailInfo.artist)
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


    override suspend fun refresh(): BaseResult<List<Artist>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<Artist>>() {}.type
    }
}