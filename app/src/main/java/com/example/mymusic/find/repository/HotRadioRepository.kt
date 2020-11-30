package com.example.mymusic.find.repository

import com.example.mymusic.find.FindApiImpl
import com.example.mymusic.find.model.HotRadio
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class HotRadioRepository: BaseMvvmRepository<List<HotRadio>>(false,"HotRadio",null) {

    override suspend fun load(): BaseResult<List<HotRadio>> {
        val hotRadioInfo = FindApiImpl.getInstance().getHotRadioInfo()
        val result: BaseResult<List<HotRadio>> = BaseResult()
        if(hotRadioInfo.code == 200){
            val resultList = hotRadioInfo.djRadios
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

    override suspend fun refresh(): BaseResult<List<HotRadio>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<HotRadio>>(){}.type

    }
}