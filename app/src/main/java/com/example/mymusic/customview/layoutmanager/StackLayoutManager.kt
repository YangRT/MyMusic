package com.example.mymusic.customview.layoutmanager

import android.animation.ObjectAnimator
import android.view.VelocityTracker
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import java.lang.reflect.Method
import kotlin.math.abs


class StackLayoutManager(val config: LayoutConfig): RecyclerView.LayoutManager(){

    private val TAG = "StackLayoutManager"

    private var mSpace = 60
    private var mUnit = 0
    private var mTotalOffset = 0
    //record the total offset without parallex
    private var mRealOffset = 0
    private lateinit var animator: ObjectAnimator
    private var animateValue = 0
    private var duration = 300
    private val lastAnimateValue = 0

    //the max stacked item count;
    private var maxStackCount = 4

    //initial stacked item
    private var initialStackCount = 4
    private var secondaryScale = 0.8f
    private var scaleRatio = 0.4f
    private var parallex = 1f
    private var initialOffset = 0
    private var initial = false
    private var mMinVelocityX = 0
    private var pointerId = 0

    val mVelocityTracker = VelocityTracker.obtain()

    private lateinit var mRV: RecyclerView
    private lateinit var recycler: RecyclerView.Recycler

    private var align: Align = Align.LEFT

    private var mItemWidth: Int = 0
    private var mItemHeight: Int = 0
    private lateinit var sSetScrollState: Method
    private var mPendingScrollPosition: Int = NO_POSITION

    init {
        isAutoMeasureEnabled = true
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    private fun absMax(a: Int, b: Int): Int {
        return if (abs(a) > abs(b)) a else b
    }
}