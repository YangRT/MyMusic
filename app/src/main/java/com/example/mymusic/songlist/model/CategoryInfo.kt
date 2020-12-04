package com.example.mymusic.songlist.model
// categories":{"0":"语种","1":"风格","2":"场景","3":"情感","4":"主题"} 不获取大分类，写死
data class SongListCategoryInfo(val code: Int, val all: SongListSubCategory, val sub: List<SongListSubCategory>)


data class SongListSubCategory(val name: String, val resourceCount: Int, val imgUrl: String, val type: Int, val category: Int, val resourceType: Int, val hot: Boolean,val activity: Boolean)