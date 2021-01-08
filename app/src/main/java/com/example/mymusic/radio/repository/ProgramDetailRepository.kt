package com.example.mymusic.radio.repository

import com.example.mymusic.base.BaseMvvmRepository
import com.example.mymusic.base.BaseResult
import com.example.mymusic.radio.RadioApiImpl
import com.example.mymusic.radio.model.DjRadio
import com.example.mymusic.radio.model.Program
import com.example.mymusic.radio.model.TopList
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @program: MyMusic
 *
 * @description: 节目详情repository
 *
 * @author: YangRT
 *
 * @create: 2021-01-08 22:17
 **/

class ProgramDetailRepository(val id: Long): BaseMvvmRepository<List<Program>>(false, "ProgramDetail$id", null) {

    override suspend fun load(): BaseResult<List<Program>> {
        val info = RadioApiImpl.getProgramDetailInfo(id)
        val result: BaseResult<List<Program>> = BaseResult()
        if (info.code == 200) {
            val list = info.programs
            result.data = list
            result.isEmpty = list.isEmpty()
            result.isFirst = true
        } else {
            result.isEmpty = true
            result.isFirst = pageNum == 0
            result.msg = info.toString()
        }
        result.isFromCache = false
        result.isPaging = false
        if (result.isFirst) {
            result.data?.let {
                saveDataToPreference(it)
            }
        }
        return result
    }

    override suspend fun refresh(): BaseResult<List<Program>> {
        return load()
    }

    override fun getTClass(): Type? {
        return  object : TypeToken<List<Program>>() {}.type
    }
}