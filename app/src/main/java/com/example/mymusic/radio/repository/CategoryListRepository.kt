package com.example.mymusic.radio.repository

import android.util.Log
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.Artist
import com.example.mymusic.radio.RadioApiImpl
import com.example.mymusic.radio.model.DjRadio
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CategoryListRepository(val id: Int): BaseMvvmRepository<List<DjRadio>>(true, "radioCategoryList$id", null) {

    override suspend fun load(): BaseResult<List<DjRadio>> {
        val info = RadioApiImpl.getCategoryListInfo(id, pageNum * 50)
        val result: BaseResult<List<DjRadio>> = BaseResult()
        if (info.code == 200) {
            pageNum = if (isRefreshing) {
                1
            } else {
                pageNum + 1
            }
            val list = info.djRadios
            result.data = list
            result.isEmpty = list.isEmpty()
            result.isFirst = pageNum == 1
        } else {
            result.isEmpty = true
            result.isFirst = pageNum == 0
            result.msg = info.toString()
        }
        result.isFromCache = false
        result.isPaging = true
        if (result.isFirst) {
            result.data?.let {
                saveDataToPreference(it)
            }
        }
        return result
    }


    override suspend fun refresh(): BaseResult<List<DjRadio>> {
        isRefreshing = true
        pageNum = 0
        return load()
    }

    suspend fun loadNextPage(): BaseResult<List<DjRadio>> {
        isRefreshing = false
        return load()
    }

    override fun getTClass(): Type? {
        return  object : TypeToken<List<DjRadio>>() {}.type
    }
}