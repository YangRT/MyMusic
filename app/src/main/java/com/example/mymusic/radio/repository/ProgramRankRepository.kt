package com.example.mymusic.radio.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.radio.RadioApiImpl
import com.example.mymusic.radio.model.TopList
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ProgramRankRepository: BaseMvvmRepository<List<TopList>>(false, "ProgramRank", null) {

    override suspend fun load(): BaseResult<List<TopList>> {
        val programRankInfo =
            RadioApiImpl.getProgramRankInfo()
        val result: BaseResult<List<TopList>> = BaseResult()
        if(programRankInfo.code == 200){
            val resultList = programRankInfo.toplist
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

    override suspend fun refresh(): BaseResult<List<TopList>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<TopList>>() {}.type
    }
}