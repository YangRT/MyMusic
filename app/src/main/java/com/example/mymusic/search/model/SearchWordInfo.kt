package com.example.mymusic.search.model

data class SearchWordInfo(var code: Int, var result: HotWords)

data class HotWords(val hots: List<HotWord>)

data class HotWord(
        val first: String,
        val second: Int,
        val third: String,
        val iconType: Int
)