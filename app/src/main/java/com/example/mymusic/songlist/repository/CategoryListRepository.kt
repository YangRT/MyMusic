package com.example.mymusic.songlist.repository

import android.util.Log
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.Artist
import com.example.mymusic.singer.SingerApiImpl
import com.example.mymusic.songlist.SongListApiImpl
import com.example.mymusic.songlist.model.CategoryList
import com.example.mymusic.songlist.model.SongListSubCategory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @program: MyMusic
 *
 * @description: 分类列表repository
 *
 * @author: YangRT
 *
 * @create: 2020-12-26 10:25
 **/

class CategoryListRepository(val cat: String): BaseMvvmRepository<List<CategoryList>>(true, "CategoryList$cat", null) {

    override suspend fun load(): BaseResult<List<CategoryList>> {
        val info = SongListApiImpl.getCategoryListInfo(pageNum * 50, cat)
        val result: BaseResult<List<CategoryList>> = BaseResult()
        if (info.code == 200) {
            pageNum = if (isRefreshing) {
                1
            } else {
                pageNum + 1
            }
            val list = info.playlists
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

    override suspend fun refresh(): BaseResult<List<CategoryList>> {
        isRefreshing = true
        pageNum = 0
        return load()
    }

    suspend fun loadNextPage(): BaseResult<List<CategoryList>> {
        isRefreshing = false
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<CategoryList>>() {}.type
    }
}