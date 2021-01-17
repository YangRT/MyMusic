package com.example.mymusic.search.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.radio.model.DjRadio
import com.example.mymusic.search.SearchApiImpl
import com.example.mymusic.search.model.SearchByLyricsSong
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @program: MyMusic
 *
 * @description: 搜索播单repository
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 17:32
 **/

class SearchRadioRepository(var word: String): BaseMvvmRepository<List<DjRadio>>(false,"SearchRadio",null) {

    override suspend fun load(): BaseResult<List<DjRadio>> {
        val info = SearchApiImpl.getInstance().getSearchRadioInfo(word)
        val result: BaseResult<List<DjRadio>> = BaseResult()
        if(info.code == 200){
            val resultList = info.result.djRadios
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

    override suspend fun refresh(): BaseResult<List<DjRadio>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<DjRadio>>(){}.type
    }

    public suspend fun search(word: String): BaseResult<List<DjRadio>> {
        this.word = word
        return load()
    }
}