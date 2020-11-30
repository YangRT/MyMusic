package com.example.mymusic.base

import com.chad.library.adapter.base.entity.MultiItemEntity


/**
 * @program: WanAndroid
 *
 * @description: 文章列表item数据类
 *
 * @author: YangRT
 *
 * @create: 2020-02-16 11:25
 **/

class BaseItemModel(type:Int):MultiItemEntity{

    lateinit var title:String
    var author:String? = null
    lateinit var time:String
    lateinit var link:String
    var type:Int = type
    var isCollect:Boolean = false
    var description:String? = null
    var imagePath:String? = null
    var classic:String? = null
    var id:Int = 0
    var originId:Int = 0

    companion object{
        const val NORMAL = 1
        const val PROJECT = 2
    }


    override val itemType: Int
        get() = type
}