package com.example.mymusic.search.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.search.SearchApiImpl
import com.example.mymusic.search.model.SearchArtist
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @program: MyMusic
 *
 * @description: 搜索歌手repository
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 17:31
 **/

class SearchSingerRepository(val word: String): BaseMvvmRepository<List<SearchArtist>>(false,"SearchSinger",null) {

    override suspend fun load(): BaseResult<List<SearchArtist>> {
        val info = SearchApiImpl.getInstance().getSearchSingerInfo(word)
        val result: BaseResult<List<SearchArtist>> = BaseResult()
        if(info.code == 200){
            val resultList = info.result.artists
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
        return result
    }

    override suspend fun refresh(): BaseResult<List<SearchArtist>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<SearchArtist>>(){}.type
    }
}