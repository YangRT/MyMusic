package com.example.wanandroid.base


/**
 * @program: WanAndroid
 *
 * @description: 返回数据结果
 *
 * @author: YangRT
 *
 * @create: 2020-02-15 11:14
 **/

class BaseResult<T> {

    var data:T? = null
    var isFirst :Boolean= false
    var isEmpty : Boolean = false
    var isFromCache:Boolean = false
    var isPaging:Boolean = false
    var msg:String? = null
}