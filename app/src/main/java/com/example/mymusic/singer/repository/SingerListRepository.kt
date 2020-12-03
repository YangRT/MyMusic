package com.example.mymusic.singer.repository

import android.util.Log
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.Artist
import com.example.mymusic.singer.SingerApiImpl
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SingerListRepository : BaseMvvmRepository<List<Artist>>(true, "singerList", null) {

    private var type: Int = -1
    private var area: Int = -1

    override suspend fun load(): BaseResult<List<Artist>> {
        val info = SingerApiImpl.getSingerListInfo(pageNum, type, area)
        val result: BaseResult<List<Artist>> = BaseResult()
        if (info.code == 200) {
            Log.e("getCa",info.artists.toString())
            pageNum = if (isRefreshing) {
                1
            } else {
                pageNum + 1
            }
            val list = info.artists
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
        if (result.isFirst && type == -1 && area == -1) {
            result.data?.let {
                saveDataToPreference(it)
            }
        }
        return result
    }

    override suspend fun refresh(): BaseResult<List<Artist>> {
        isRefreshing = true
        pageNum = 0
        return load()
    }

    suspend fun loadNextPage(): BaseResult<List<Artist>> {
        isRefreshing = false
        return load()
    }

    suspend fun setParams(type: Int, area: Int): BaseResult<List<Artist>> {
        this.type = type
        this.area = area
        isRefreshing = true
        pageNum = 0
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<Artist>>() {}.type
    }
}