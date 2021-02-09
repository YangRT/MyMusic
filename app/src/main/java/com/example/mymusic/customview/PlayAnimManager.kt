package com.example.mymusic.customview

import android.R
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.LinearLayout


class PlayAnimManager {

    var mListener: AnimListener? = null
    private var time: Long = 800
    private var animLayout: ViewGroup? = null

    private fun createAnimLayout(mainActivity: Activity): ViewGroup? {
        val rootView = mainActivity.window.decorView as ViewGroup
        val animLayout = LinearLayout(mainActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        animLayout.layoutParams = lp
        val id = Int.MAX_VALUE
        animLayout.id = id
        animLayout.setBackgroundResource(R.color.transparent)
        rootView.addView(animLayout)
        return animLayout
    }

    private fun addViewToAnimLayout(
        vg: ViewGroup, view: View,
        location: IntArray
    ): View? {
        val x = location[0]
        val y = location[1]
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.leftMargin = x
        lp.topMargin = y
        view.setLayoutParams(lp)
        return view
    }

    fun setTime(l: Long) {
        time = l

    }

    fun setAnim(
        mainActivity: Activity?,
        v: View,
        start_location: IntArray,
        end_location: IntArray
    ) {
        animLayout = null
        animLayout = createAnimLayout(mainActivity!!)
        animLayout?.addView(v) // 把动画小球添加到动画层
        val view = addViewToAnimLayout(animLayout!!, v, start_location)

        //终点位置
        val endX = end_location[0] - start_location[0] + 20
        val endY = end_location[1] - start_location[1] // 动画位移的y坐标
        val translateAnimationX = TranslateAnimation(0f, endX.toFloat(), 0f, 0f)
        translateAnimationX.interpolator = LinearInterpolator()
        translateAnimationX.repeatCount = 0 // 动画重复执行的次数
        translateAnimationX.fillAfter = true
        val translateAnimationY = TranslateAnimation(0f, 0f, 0f, endY.toFloat())
        translateAnimationY.interpolator = AccelerateInterpolator()
        translateAnimationY.repeatCount = 0
        translateAnimationX.fillAfter = true
        val set = AnimationSet(false)
        set.fillAfter = false
        set.addAnimation(translateAnimationY)
        set.addAnimation(translateAnimationX)
        set.duration = time // 动画的执行时间
        view!!.startAnimation(set)
        // 动画监听事件
        set.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                v.visibility = View.VISIBLE
                mListener?.setAnimBegin(this@PlayAnimManager)
            }

            override fun onAnimationRepeat(animation: Animation) {}

            // 动画的结束调用的方法
            override fun onAnimationEnd(animation: Animation) {
                v.visibility = View.GONE
                mListener?.setAnimEnd(this@PlayAnimManager)
                animLayout?.removeAllViews()
            }
        })
    }

    fun setOnAnimListener(listener: AnimListener) {
        mListener = listener
    }

    //回调监听
    interface AnimListener {
        fun setAnimBegin(a: PlayAnimManager?)
        fun setAnimEnd(a: PlayAnimManager?)
    }
}