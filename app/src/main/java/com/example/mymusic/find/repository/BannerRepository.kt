package com.example.mymusic.find.repository

import com.example.mymusic.find.FindApiImpl
import com.example.mymusic.find.model.BannerData
import com.example.mymusic.find.model.BannerInfo
import com.example.wanandroid.base.BaseMvvmRepository
import com.example.wanandroid.base.BaseResult
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BannerRepository: BaseMvvmRepository<List<BannerData>>(false,"banner",null) {

    override suspend fun load(): BaseResult<List<BannerData>> {
        val bannerInfo = FindApiImpl.getInstance().getBanner()
        val result:BaseResult<List<BannerData>> = BaseResult()
        if(bannerInfo.code == 200){
            val resultList = bannerInfo.banners
            result.isEmpty = resultList.size == 0
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

    override suspend fun refresh(): BaseResult<List<BannerData>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<BannerData>>(){}.type
    }

}