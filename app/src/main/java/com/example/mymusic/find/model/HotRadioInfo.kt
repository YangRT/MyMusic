package com.example.mymusic.find.model

data class HotRadioInfo(val code: Int, val hasMore: Boolean, val djRadios: List<HotRadio>)

data class HotRadio(val id: Long, val picUrl: String, val name: String, val playCount: Int)

