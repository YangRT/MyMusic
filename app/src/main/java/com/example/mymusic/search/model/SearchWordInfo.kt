package com.example.mymusic.search.model

data class SearchWordInfo(var code: Int, var result: HotWords? = null)

data class HotWords(val hotWords: List<HotWord>? = null)

data class HotWord(
        val first: String? = null,
        val second :Int,
        val third: String? = null,
        val iconType: Int
)