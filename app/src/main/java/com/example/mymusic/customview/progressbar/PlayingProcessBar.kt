package com.example.mymusic.customview.progressbar

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.mymusic.R


//  音乐播放进度条
class PlayingProcessBar@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int = 0):
    View(context,attributeSet,defStyleAttr) {

    private var canModify : Boolean = true
    private var isInit: Boolean = false
    private var mListener: ProcessChangeListener? = null

    // 进度icon
    private var playingIcon: Drawable? = null

    // 进度相关
    var totalSeconds: Int = 100
    var currentSecond: Int = 20
    var currentProcessText: String = "00:00"
    var totalProcessText: String = "00:00"

    var textSize = 0
    var textColor = Color.BLACK

    var processBackgroundColor = Color.LTGRAY
    var processPlayingColor = Color.parseColor("#3A86F0")
    var processBarWidth = 10
    var processBarTotalLength = 0
    var processBarCurrentLength = 0

    var textPaint: Paint
    var processBarBackgroundPaint: Paint
    var processBarPaint: Paint

    var viewWidth = 0
    var viewHeight = 0
    var drawY = 0

    private var textBound = Rect()

    init {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.PlayingProcessBar)
        textColor = typeArray.getColor(R.styleable.PlayingProcessBar_textColor, Color.WHITE)
        textSize = typeArray.getDimensionPixelSize(R.styleable.PlayingProcessBar_textSize, 40)
        processBackgroundColor = typeArray.getColor(R.styleable.PlayingProcessBar_backgroundColor, Color.parseColor("#3A86F0"))
        processPlayingColor = typeArray.getColor(R.styleable.PlayingProcessBar_processBarColor, Color.WHITE)
        processBarWidth = typeArray.getDimensionPixelSize(R.styleable.PlayingProcessBar_processBarWidth, 4)
        typeArray.recycle()

        textPaint = Paint()
        textPaint.color = textColor
        textPaint.isAntiAlias = true
        textPaint.textSize = textSize.toFloat()

        processBarBackgroundPaint = Paint()
        processBarBackgroundPaint.color = processBackgroundColor
        processBarBackgroundPaint.strokeWidth = processBarWidth.toFloat()

        processBarPaint = Paint()
        processBarPaint.color = processPlayingColor
        processBarPaint.strokeWidth = processBarWidth.toFloat()

        playingIcon = ResourcesCompat.getDrawable(resources, R.drawable.music, null)
        totalProcessText = secondToTime(totalSeconds)
        currentProcessText = secondToTime(currentSecond)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (viewHeight != h || viewWidth != w) {
            viewHeight = h
            viewWidth = w
            initView()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 画文字
        textPaint.getTextBounds(totalProcessText,0, totalProcessText.length, textBound)
        val textWidth = textBound.width()
        //计算baseline
        val fontMetrics=textPaint.fontMetrics
        val distance= (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom
        val baseline= drawY + distance
        canvas?.drawText(currentProcessText, dp2px(4).toFloat(), baseline, textPaint)
        val processLength = viewWidth - 2 * textWidth - dp2px(16).toFloat()
        if (processLength < 0) {
            return
        }
        canvas?.drawText(totalProcessText, dp2px(12).toFloat() + processLength + textWidth, baseline, textPaint)

        // 画进度条
        canvas?.drawLine(textWidth + dp2px(8).toFloat(), drawY.toFloat(),textWidth + dp2px(8).toFloat() + processLength, drawY.toFloat(), processBarBackgroundPaint)
        var currentLength = 0F
        if (totalSeconds != 0) {
            currentLength = currentSecond.toFloat() / totalSeconds * processLength;
            canvas?.drawLine(textWidth + dp2px(8).toFloat(), drawY.toFloat(),textWidth + dp2px(8).toFloat() + currentLength, drawY.toFloat(), processBarPaint)
        }
        // 画icon
        playingIcon?.let {
            it.setBounds(textWidth + dp2px(8) + currentLength.toInt() - it.intrinsicWidth/2, drawY - it.intrinsicHeight/2,textWidth + dp2px(8) + currentLength.toInt()+it.intrinsicWidth/2, drawY + it.intrinsicHeight/2)
            canvas?.let {canvas ->
                it.draw(canvas)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!canModify || !isInit) {
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun initView() {
        drawY = viewHeight / 2
        if (isInit) {
            invalidate()
        }
    }


    // 改变进度
    public fun changeProcess(second: Int) {
        if (!isInit || second > totalSeconds) {
            return
        }
        currentSecond = second
        currentProcessText = secondToTime(second)
        processBarWidth = second/totalSeconds * processBarTotalLength
        invalidate()
    }

    public fun setProcessChangeListener(listener: ProcessChangeListener) {
        mListener = listener
    }



    public fun setCanModify(type: Boolean) {
        canModify = type
    }

    public fun initProcessBar(second: Int) {
        if (!isInit) {
            totalSeconds = second
            totalProcessText = secondToTime(second)
            isInit = true
            invalidate()
        }
    }


    // 转化文字
    private fun secondToTime(second: Int): String {
        var temp = second
        val hour = temp / 3600
        temp -= 3600 * hour
        val minute = temp / 60
        temp -= 60 * minute
        val sb = StringBuilder()
        if (hour != 0){
            if (hour < 10) {
                sb.append(0)
            }
            sb.append(hour)
            sb.append(":")
        }
        if (minute != 0) {
            if (minute < 10) {
                sb.append(0)
            }
            sb.append(minute)
        }else {
            sb.append("00")
        }
        sb.append(":")
        if (temp == 0) {
            sb.append("00")
        }else {
            if (temp < 10) {
                sb.append(0)
            }
            sb.append(temp)
        }
        return sb.toString()
    }

    private fun dp2px(dp: Int): Int {
        val scale =
            Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }


    public interface ProcessChangeListener{

        fun change(second: Int)
    }
}