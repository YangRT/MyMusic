package com.example.mymusic.rank.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.Artist
import com.example.mymusic.find.FindApiImpl
import com.example.mymusic.find.model.BannerData
import com.example.mymusic.rank.RankApiImpl
import com.example.mymusic.rank.model.RankInfo
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AllRankRepository: BaseMvvmRepository<List<RankInfo>>(false, "allRank", null) {

    override suspend fun load(): BaseResult<List<RankInfo>> {
        val allRankInfo = RankApiImpl.getAllRankInfo()
        val result: BaseResult<List<RankInfo>> = BaseResult()
        if(allRankInfo.code == 200){
            val resultList = allRankInfo.list
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

    override suspend fun refresh(): BaseResult<List<RankInfo>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<RankInfo>>() {}.type
    }
}