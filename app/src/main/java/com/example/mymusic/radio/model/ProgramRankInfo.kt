package com.example.mymusic.radio.model

data class ProgramRankInfo(val code: Int, val updateTime: Long, val toplist: List<TopList>)

data class TopList(val rank: Int, val programFeeType: Int, val score: Int, val program: Program)

data class Program(val id: Long, val name: String, val nickname: String, val description: String)