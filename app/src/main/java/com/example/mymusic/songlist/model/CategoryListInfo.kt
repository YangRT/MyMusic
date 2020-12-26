package com.example.mymusic.songlist.model

import android.os.Parcelable.Creator


/**
 * @program: MyMusic
 *
 * @description:
 *
 * @author: YangRT
 *
 * @create: 2020-12-26 10:17
 **/

data class CategoryListInfo(val code: Int, val total: Int, val more: Boolean, val cat: String, val playlists: List<CategoryList>)

data class CategoryList(val name: String, val id: Long, val coverImgUrl: String, val description: String, val playCount: Int)
