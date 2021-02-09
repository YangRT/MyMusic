package com.example.mymusic.customview

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.widget.PopupWindowCompat
import com.example.mymusic.R

class PlayBottomWindow(context: Context, builder: ConfirmPopupWindowBuilder? = null) : PopupWindow() {


    init {
        val inflater = LayoutInflater.from(context)
        this.contentView = inflater.inflate(R.layout.window_bottom_play, null) //布局xml
        this.width = LinearLayout.LayoutParams.MATCH_PARENT //父布局减去padding
        this.height = LinearLayout.LayoutParams.WRAP_CONTENT
        this.isOutsideTouchable = false //是否可以
        this.isClippingEnabled = false //背景透明化可以铺满全屏
//        //设置取消的点击事件
//        this.contentView.tv_confirm_cancel.setOnClickListener {
//            //先隐藏弹窗
//            dismiss()
//            //调用接口的具体实现
//            builder?.mCancelListener?.invoke()
//        }
//
//        //设置确认的点击事件
//        this.contentView.tv_confirm_ensure.setOnClickListener {
//            //先隐藏弹窗
//            dismiss()
//            builder?.mConfirmListener?.invoke()
//        }
    }


    class ConfirmPopupWindowBuilder(val context: Context) {
        companion object {
            fun init(context: Context): ConfirmPopupWindowBuilder {
                return ConfirmPopupWindowBuilder(context)
            }
        }

        var mCancelListener: () -> Unit = {} //一个不需要参数的无返回值的函数
        var mConfirmListener: () -> Unit = {}

        private val window: PlayBottomWindow = PlayBottomWindow(context, this)

        fun build(): PlayBottomWindow {
//            window.contentView.tv_confirm_content.visibility = View.VISIBLE
//            window.contentView.tv_confirm_cancel.visibility = View.VISIBLE
//            window.contentView.tv_confirm_ensure.visibility = View.VISIBLE
            return window
        }

        fun setContent(content: String): ConfirmPopupWindowBuilder {
//            window.contentView.tv_confirm_content.visibility = View.VISIBLE
//            window.contentView.tv_confirm_content.text = content
            return this
        }

        fun setCancelListener(callback: () -> Unit): ConfirmPopupWindowBuilder {
            mCancelListener = callback
            return this
        }

        fun setEnsureListener(callback: () -> Unit): ConfirmPopupWindowBuilder {
            mConfirmListener = callback
            return this
        }
    }

    fun show() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
    }
}
