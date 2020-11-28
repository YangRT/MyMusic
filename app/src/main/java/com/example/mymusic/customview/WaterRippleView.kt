package com.example.mymusic.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.mymusic.R
import com.example.mymusic.utils.UiUtils.dipToPx


class WaterRippleView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    // y = Asin(wx+b)+h
    private val STRETCH_FACTOR_A = 20f
    private val OFFSET_Y = 20

    // 第一条水波移动速度
    private val TRANSLATE_X_SPEED_ONE = 7

    // 第二条水波移动速度
    private val TRANSLATE_X_SPEED_TWO = 5
    private var mCycleFactorW = 0f

    private var mTotalWidth = 0
    private var mTotalHeight: Int = 0
    private var mYPositions: FloatArray = floatArrayOf()
    private var mResetOneYPositions: FloatArray = floatArrayOf()
    private var mResetTwoYPositions: FloatArray = floatArrayOf()
    private var mXOffsetSpeedOne = 0
    private var mXOffsetSpeedTwo = 0
    private var mXOneOffset = 0
    private var mXTwoOffset = 0

    private var mWavePaint: Paint
    private var mDrawFilter: DrawFilter

    init {
        // 将dp转化为px，用于控制不同分辨率上移动速度基本一致

        // 将dp转化为px，用于控制不同分辨率上移动速度基本一致
        mXOffsetSpeedOne = dipToPx(context, TRANSLATE_X_SPEED_ONE)
        mXOffsetSpeedTwo = dipToPx(context, TRANSLATE_X_SPEED_TWO)

        // 初始绘制波纹的画笔

        // 初始绘制波纹的画笔
        mWavePaint = Paint()
        // 去除画笔锯齿
        // 去除画笔锯齿
        mWavePaint.isAntiAlias = true
        // 设置风格为实线
        // 设置风格为实线
        mWavePaint.style = Paint.Style.FILL
        // 设置画笔颜色
        // 设置画笔颜色\波纹颜色
        mWavePaint.color = this.resources.getColor(R.color.colorMain)
        mDrawFilter = PaintFlagsDrawFilter(
            0, Paint.ANTI_ALIAS_FLAG
                    or Paint.FILTER_BITMAP_FLAG
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 从canvas层面去除绘制时锯齿
        // 从canvas层面去除绘制时锯齿
        canvas!!.drawFilter = mDrawFilter
        resetPositionY()
        for (i in 0 until mTotalWidth) {

            // 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
            // 绘制第一条水波纹
            canvas.drawLine(
                i.toFloat(), mTotalHeight - mResetOneYPositions[i] - 1160, i.toFloat(),
                mTotalHeight.toFloat(), mWavePaint
            )

            // 绘制第二条水波纹
            canvas.drawLine(
                i.toFloat(), mTotalHeight - mResetTwoYPositions[i] - 1160, i.toFloat(),
                mTotalHeight.toFloat(), mWavePaint
            )
        }

        // 改变两条波纹的移动点

        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne
        mXTwoOffset += mXOffsetSpeedTwo

        // 如果已经移动到结尾处，则重头记录

        // 如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mTotalWidth) {
            mXOneOffset = 0
        }
        if (mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0
        }

        // 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片

        // 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
        postInvalidate()
    }

    private fun resetPositionY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        val yOneInterval: Int = mYPositions.size - mXOneOffset
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(
            mYPositions, mXOneOffset, mResetOneYPositions, 0,
            yOneInterval
        )
        System.arraycopy(
            mYPositions, 0, mResetOneYPositions, yOneInterval,
            mXOneOffset
        )
        val yTwoInterval: Int = mYPositions.size - mXTwoOffset
        System.arraycopy(
            mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
            yTwoInterval
        )
        System.arraycopy(
            mYPositions, 0, mResetTwoYPositions, yTwoInterval,
            mXTwoOffset
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 记录下view的宽高
        // 记录下view的宽高
        mTotalWidth = w
        mTotalHeight = h
        // 用于保存原始波纹的y值
        // 用于保存原始波纹的y值
        mYPositions = FloatArray(mTotalWidth)
        // 用于保存波纹一的y值
        // 用于保存波纹一的y值
        mResetOneYPositions = FloatArray(mTotalWidth)
        // 用于保存波纹二的y值
        // 用于保存波纹二的y值
        mResetTwoYPositions = FloatArray(mTotalWidth)

        // 将周期定为view总宽度

        // 将周期定为view总宽度
        mCycleFactorW = (2 * Math.PI / mTotalWidth).toFloat()

        // 根据view总宽度得出所有对应的y值

        // 根据view总宽度得出所有对应的y值
        for (i in 0 until mTotalWidth) {
            mYPositions[i] = (STRETCH_FACTOR_A
                    * Math.sin(mCycleFactorW * i.toDouble()) + OFFSET_Y).toFloat()
        }
    }
}

