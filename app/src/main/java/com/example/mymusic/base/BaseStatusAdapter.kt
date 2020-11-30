package com.example.mymusic.base

import android.view.LayoutInflater
import android.view.View
import com.alguojian.mylibrary.DefaultLoadingView
import com.alguojian.mylibrary.StatusAdapter
import com.alguojian.mylibrary.StatusLayout
import com.example.mymusic.R

/**
 * @program: WanAndroid
 *
 * @description: 状态页 适配器
 *
 * @author: YangRT
 *
 * @create: 2020-02-19 15:29
 **/

class BaseStatusAdapter :StatusAdapter{
    override fun getView(
        statusHelper: StatusLayout.StatusHelper,
        statusView: View?,
        status: Int
    ): View? {
        var view : View? = null
        if(status == StatusLayout.STATUS_LAYOUT_STATUS_EMPTY){
            view = LayoutInflater.from(statusHelper.context!!).inflate(R.layout.status_empty,null)
            view?.setOnClickListener { statusHelper.click?.invoke() }
        }else if (status == StatusLayout.STATUS_LAYOUT_STATUS_FAIL){
            view = LayoutInflater.from(statusHelper.context!!).inflate(R.layout.status_error,null)
            view?.setOnClickListener { statusHelper.click?.invoke() }
        }else if(status == StatusLayout.STATUS_LAYOUT_STATUS_LOADING){
            view = LayoutInflater.from(statusHelper.context!!).inflate(R.layout.status_loading,null)
            view?.setOnClickListener { statusHelper.click?.invoke() }
        }else{
            view  = DefaultLoadingView(statusHelper.context!!, statusHelper.click)
            view.setStatus(status)
        }
        return view
    }
}