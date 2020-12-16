package com.example.mymusic.radio.model

data class RadioBannerInfo(val code: Int, val data: List<RadioBanner>)

data class RadioBanner(val targetId: Long, val targetType: Int, val pic: String, val url: String, val typeTitle: String, val exclusive: Boolean)