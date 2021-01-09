package com.example.mymusic.search.model

import com.example.mymusic.radio.model.DjRadio


/**
 * @program: MyMusic
 *
 * @description: 搜索播单info
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 17:27
 **/

data class SearchRadioInfo(val code: Int, val needLogin: Boolean, val result: SearchRadioResult)

data class SearchRadioResult(val djRadiosCount: Int, val djRadios: List<DjRadio>)