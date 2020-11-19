package com.example.wanandroid.base


/**
 * @program: WanAndroid
 *
 * @description: 数据请求状态
 *
 * @author: YangRT
 *
 * @create: 2020-02-15 11:40
 **/

enum class PageStatus {

    LOADING,
    EMPTY,
    SHOW_CONTENT,
    NO_MORE_DATA,
    REFRESH_ERROR,
    LOAD_MORE_FAILED,
    REQUEST_ERROR,
    NETWORK_ERROR
}