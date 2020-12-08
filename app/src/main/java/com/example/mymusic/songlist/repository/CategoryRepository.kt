package com.example.mymusic.songlist.repository

import android.util.Log
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.songlist.SongListApiImpl
import com.example.mymusic.songlist.model.SongListSubCategory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CategoryRepository: BaseMvvmRepository<List<SongListSubCategory>>(false, "SubCategory", null){

    override suspend fun load(): BaseResult<List<SongListSubCategory>> {
        val info = SongListApiImpl.getCategoryInfo()
        val result: BaseResult<List<SongListSubCategory>> = BaseResult()
        if (info.code == 200) {
            Log.e("getCa",info.sub.toString())
            val list = info.sub
            list.sortedBy { it.category }
            result.data = list
            result.isEmpty = list.isEmpty()
            result.isFirst = pageNum == 0
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

    override suspend fun refresh(): BaseResult<List<SongListSubCategory>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<SongListSubCategory>>() {}.type
    }
}