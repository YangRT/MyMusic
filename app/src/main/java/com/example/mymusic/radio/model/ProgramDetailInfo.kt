package com.example.mymusic.radio.model


/**
 * @program: MyMusic

 *
 * @description: 节目详情info
 *
 * @author: YangRT
 *
 * @create: 2021-01-08 21:45
 **/

data class ProgramDetailInfo(val code: Int, val count: Int, val programs: List<Program>,val more: Boolean)