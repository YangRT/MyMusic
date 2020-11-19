package com.example.mymusic.find.model

data class BannerInfo(val banners: List<BannerData>,
                      val code: Int)

data class BannerData(
    val targetId: Int,
    val imageUrl: String,
    val typeTitle: String,
    val targetType: Int,
    val url: String
)