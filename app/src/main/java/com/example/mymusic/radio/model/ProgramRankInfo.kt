package com.example.mymusic.radio.model

import com.example.mymusic.base.model.Artist

data class ProgramRankInfo(val code: Int, val updateTime: Long, val toplist: List<TopList>)

data class TopList(val rank: Int, val programFeeType: Int, val score: Int, val program: Program)

data class Program(val id: Long, val name: String, val description: String, val coverUrl: String, val mainSong: MainSong)

data class MainSong(val id: Long, val name: String, val disc: String, val artists: List<Artist>, val duration: String)