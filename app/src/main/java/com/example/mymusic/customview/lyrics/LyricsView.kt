package com.example.mymusic.customview.lyrics

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.View
import com.example.mymusic.R


/**
 * @program: MyMusic
 *
 * @description: 歌词显示控件
 *
 * @author: YangRT
 *
 * @create: 2021-04-15 23:32
 **/

class LyricsView@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var list: ArrayList<LyricsData> = ArrayList()
    private lateinit var gPaint: Paint
    private lateinit var hPaint: Paint
    private var mWidth = 0
    private var mHeight:Int = 0
    private var currentPosition = 0
    private val player: MediaPlayer? = null
    private var lastPosition = 0
    private var highLineColor = 0
    private var lrcColor = 0
    private var mode = 0
    val KARAOKE = 1

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LyricsView)
        highLineColor =
            ta.getColor(R.styleable.LyricsView_hignLineColor, resources.getColor(R.color.colorMain))
        lrcColor = ta.getColor(R.styleable.LyricsView_lrcColor, resources.getColor(R.color.black)
        )
        mode = ta.getInt(R.styleable.LyricsView_lrcMode, mode)
        ta.recycle()
        gPaint = Paint()
        gPaint.isAntiAlias = true
        gPaint.color = lrcColor
        gPaint.textSize = 36f
        gPaint.textAlign = Paint.Align.CENTER
        hPaint = Paint()
        hPaint.isAntiAlias = true
        hPaint.color = highLineColor
        hPaint.textSize = 36f
        hPaint.textAlign = Paint.Align.CENTER
    }

    fun setLrc(lrc: String) {
        list = LyricsParser.parseLyrics(lrc)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (list.size == 0) {
            canvas?.drawText("暂无歌词", width.toFloat() / 2, height.toFloat() / 2, gPaint)
            return
        }
        getCurrentPosition()
        
    }

    fun init() {
        currentPosition = 0
        lastPosition = 0
        scrollY = 0
        invalidate()
    }

    private fun getCurrentPosition() {
        try {
            val currentMillis = player!!.currentPosition
            if (currentMillis < list[0].getStart()) {
                currentPosition = 0
                return
            }
            if (currentMillis > list[list.size - 1].getStart()) {
                currentPosition = list.size - 1
                return
            }
            for (i in list.indices) {
                if (currentMillis >= list[i].getStart() && currentMillis < list[i]
                        .getEnd()
                ) {
                    currentPosition = i
                    return
                }
            }
        } catch (e: Exception) {
//            e.printStackTrace();
            postInvalidateDelayed(100)
        }
    }
}