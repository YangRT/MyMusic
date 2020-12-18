package com.example.mymusic.songlist.repository

import android.util.Log
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.songlist.SongListApiImpl
import com.example.mymusic.songlist.model.SongListSubCategory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CategoryRepository: BaseMvvmRepository<List<SongListSubCategory>>(false, "SubCategory", null){

    private val titleList = ArrayList<SongListSubCategory>()

    override suspend fun load(): BaseResult<List<SongListSubCategory>> {
        addTitle()
        val info = SongListApiImpl.getCategoryInfo()
        val result: BaseResult<List<SongListSubCategory>> = BaseResult()
        if (info.code == 200) {
            Log.e("getCa",info.sub.toString())
            val list = info.sub
            list.sortedBy { it.category }
            result.data = list
            result.isEmpty = list.isEmpty()
            if (list.size > 0) {
                list.addAll(titleList)
                list.sortWith(compareBy(SongListSubCategory::category,SongListSubCategory::type))
            }
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

    private fun addTitle() {
        titleList.clear()
        titleList.add(SongListSubCategory("语种",-1,"",-1,0,-1, hot = false, activity = false))
        titleList.add(SongListSubCategory("风格",-1,"",-1,1,-1, hot = false, activity = false))
        titleList.add(SongListSubCategory("场景",-1,"",-1,2,-1, hot = false, activity = false))
        titleList.add(SongListSubCategory("情感",-1,"",-1,3,-1, hot = false, activity = false))
        titleList.add(SongListSubCategory("主题",-1,"",-1,4,-1, hot = false, activity = false))
    }
}