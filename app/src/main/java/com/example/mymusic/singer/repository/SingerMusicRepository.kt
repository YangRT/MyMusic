package com.example.mymusic.singer.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.base.model.HotSong
import com.example.mymusic.singer.SingerApiImpl
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SingerMusicRepository(private val id: Long): BaseMvvmRepository<List<HotSong>>(false, id.toString(), null) {

    override suspend fun load(): BaseResult<List<HotSong>> {
        val musicInfo = SingerApiImpl.getSingerMusicInfo(id)
        val result: BaseResult<List<HotSong>> = BaseResult()
        if(musicInfo.code == 200){
            val resultList = musicInfo.hotSongs
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
//        if(result.isFirst){
//            result.data?.let {
//                saveDataToPreference(it)
//            }
//        }
        return result
    }

    override suspend fun refresh(): BaseResult<List<HotSong>> {
        return load()
    }

    override fun getTClass(): Type? {
        return object : TypeToken<List<HotSong>>() {}.type
    }
}