package com.example.mymusic.radio.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.radio.RadioApiImpl
import com.example.mymusic.radio.model.RadioCategory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RadioCategoryRepository: BaseMvvmRepository<List<RadioCategory>>(false, "RadioCategory", null) {

    override suspend fun load(): BaseResult<List<RadioCategory>> {
        val radioCategoryInfo =
            RadioApiImpl.getRadioCategoryInfo()
        val result: BaseResult<List<RadioCategory>> = BaseResult()
        if(radioCategoryInfo.code == 200){
            val resultList = radioCategoryInfo.categories
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

    override suspend fun refresh(): BaseResult<List<RadioCategory>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<RadioCategory>>() {}.type
    }
}