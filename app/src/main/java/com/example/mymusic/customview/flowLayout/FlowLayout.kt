package com.example.mymusic.customview.flowLayout

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup


class FlowLayout@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int = 0):
    ViewGroup(context,attributeSet,defStyleAttr) {

    //可用宽度
    private var usefulWidth = 0

    //子view x轴初始位置
    private var childrenLeft = 0

    //已用宽度
    private var usedWidth = 0

    //总行数
    private var totalLines = 0

    //总高度
    private var totalHeight = 0

    //每行子View的最大高度
    private var lineMaxHeight = 0

    //存储每个子view位置信息
    private val viewLocationList: ArrayList<ViewLocation> = ArrayList()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //每行子View的最大宽度
        var lineMaxWidth = paddingLeft
        //设置初值
        totalHeight = paddingTop
        usedWidth = paddingLeft
        childrenLeft = paddingLeft
        if(viewLocationList.isNotEmpty()){
            //及时清空，避免布局混乱
            viewLocationList.clear()
        }
        //获取限制大小
        //获取限制大小
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        usefulWidth = widthSize
        val childCount = childCount
        //遍历子View,计算子view位置以及布局大小
        //遍历子View,计算子view位置以及布局大小
        if (childCount > 0) {
            totalLines = 1
            for (i in 0 until childCount) {
                val v: View = getChildAt(i)
                measureChild(v, widthMeasureSpec, heightMeasureSpec)
                //获取子view属性
                val lp = v.layoutParams as MarginLayoutParams
                //判断是否换行
                if (v.measuredWidth + lp.leftMargin <= usefulWidth) {
                    setLocation(v, lp)
                } else {
                    //换行更新或重置各变量
                    totalLines++
                    lineMaxWidth = if (lineMaxWidth >= usedWidth) lineMaxWidth else usedWidth
                    totalHeight += lineMaxHeight
                    lineMaxHeight = 0
                    usefulWidth = widthSize
                    usedWidth = paddingLeft
                    childrenLeft = paddingLeft
                    //直接添加，即使新的一行也装不下
                    setLocation(v, lp)
                }
            }
        }
        //判断最后一行
        //判断最后一行
        lineMaxWidth = if (lineMaxWidth >= usedWidth) lineMaxWidth else usedWidth
        totalHeight += lineMaxHeight + paddingBottom

        //使wrap_content属性起作用
        //使wrap_content属性起作用
        if (layoutParams.width == LayoutParams.WRAP_CONTENT && layoutParams.height == LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(lineMaxWidth, totalHeight)
        } else if (layoutParams.width == LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(lineMaxWidth, heightSize)
        } else if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, totalHeight)
        } else {
            setMeasuredDimension(widthSize, heightSize)
        }
    }

    //保存各view的位置参数
    private fun setLocation(v: View, lp: MarginLayoutParams) {
        val mLocation = ViewLocation()
        mLocation.setLeft(attr.left + lp.leftMargin)
        //getWidth() layout后才能获取到值，getMeasuredWidth()测量后就获取到值
        mLocation.setRight(mLocation.getLeft() + v.measuredWidth)
        mLocation.setTop(totalHeight + lp.topMargin)
        mLocation.setBottom(mLocation.getTop() + v.measuredHeight)
        mLocation.setMyLine(totalLines)
        usefulWidth -= mLocation.getRight() + lp.rightMargin - mLocation.getLeft()
        usedWidth += mLocation.getRight() + lp.rightMargin - mLocation.getLeft() + lp.leftMargin
        childrenLeft += usedWidth
        lineMaxHeight =
            if (mLocation.getBottom() - mLocation.getTop() + lp.bottomMargin + lp.topMargin >= lineMaxHeight) mLocation.getBottom() - mLocation.getTop() + lp.bottomMargin + lp.topMargin else lineMaxHeight
        viewLocationList.add(mLocation)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            v.layout(
                viewLocationList[i].getLeft(),
                viewLocationList[i].getTop(),
                viewLocationList[i].getRight(),
                viewLocationList[i].getBottom()
            )
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}