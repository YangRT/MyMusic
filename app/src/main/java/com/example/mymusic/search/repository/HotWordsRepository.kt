package com.example.mymusic.search.repository

import com.example.mymusic.search.SearchApiImpl
import com.example.mymusic.search.model.HotWord
import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @program: MyMusic
 *
 * @description: 搜索热词repository
 *
 * @author: YangRT
 *
 * @create: 2020-11-25 22:47
 **/

class HotWordsRepository: BaseMvvmRepository<List<HotWord>>(false,"hotword",null) {
    override suspend fun load(): BaseResult<List<HotWord>> {
        val searchWordInfo = SearchApiImpl.getInstance().getHotWordsInfo()
        val result: BaseResult<List<HotWord>> = BaseResult()
        if(searchWordInfo.code == 200){
            val resultList = searchWordInfo.result.hots
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

    override suspend fun refresh(): BaseResult<List<HotWord>> {
        return refresh()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<HotWord>>(){}.type
    }
}