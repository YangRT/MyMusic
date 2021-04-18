package com.example.mymusic.customview.lyrics

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.media.MediaPlayer
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.mymusic.R
import okio.utf8Size
import org.w3c.dom.Text


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
    private lateinit var gPaint: TextPaint
    private lateinit var hPaint: TextPaint
    private var mWidth = 0
    private var mHeight:Int = 0
    private var currentPosition = 0
    private val player: MediaPlayer? = null
    private var lastPosition = 0
    private var highLineColor = 0
    private var lrcColor = 0
    private var mode = 0
    val KARAOKE = 1
    private var currentMillis = 0L
    private var moreHeight = 0


    init {
        // 获取自定义属性
        val ta: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LyricsView)
        highLineColor =
            ta.getColor(R.styleable.LyricsView_hignLineColor, resources.getColor(R.color.colorMain))
        lrcColor = ta.getColor(R.styleable.LyricsView_lrcColor, resources.getColor(R.color.black)
        )
        mode = ta.getInt(R.styleable.LyricsView_lrcMode, mode)
        ta.recycle()
        // 初始化
        gPaint = TextPaint()
        gPaint.isAntiAlias = true
        gPaint.color = lrcColor
        gPaint.textSize = 36f
        gPaint.textAlign = Paint.Align.CENTER
        hPaint = TextPaint()
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
        canvas?.let {
            if (list.size == 0) {
                canvas?.drawText("暂无歌词", width.toFloat() / 2, height.toFloat() / 2, gPaint)
                return
            }
            getCurrentPosition()
            drawLrc(canvas, currentMillis)
            val start = list[currentPosition].getStart()
            val v = (currentPosition * 80).toFloat()
            scrollY = v.toInt()
            if (scrollY == currentPosition * 100) {
                lastPosition = currentPosition
            }
            postInvalidateDelayed(100)
        }
    }

    private fun drawLrc(canvas: Canvas, currentMillis: Long) {
        if (mode == 0) {
            for (i in list.indices) {
                val lrc = list[i].getLyrics()
                val lrcWidth = hPaint.measureText(lrc)
                if (lrcWidth >= width) {
                    if (i == currentPosition) {
                        val staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            StaticLayout.Builder.obtain(lrc, 0, lrc.length, hPaint, width).setMaxLines(1).setEllipsize(TextUtils.TruncateAt.MARQUEE).build()
                        } else {
                            StaticLayout(lrc, hPaint, width, Layout.Alignment.ALIGN_CENTER, 0f, 0f, false)
                        }
                        canvas.drawText(list[i].getLyrics(), (width / 2).toFloat(), (height / 2 + 80 * i + moreHeight).toFloat(), hPaint)
                    } else {
                        canvas.drawText(list[i].getLyrics(), (width / 2).toFloat(), (height / 2 + 80 * i+ moreHeight).toFloat(), gPaint)
                    }
                } else {
                    if (i == currentPosition) {
                        canvas.drawText(list[i].getLyrics(), (width / 2).toFloat(), (height / 2 + 80 * i + moreHeight).toFloat(), hPaint)
                    } else {
                        canvas.drawText(list[i].getLyrics(), (width / 2).toFloat(), (height / 2 + 80 * i+ moreHeight).toFloat(), gPaint)
                    }
                }
            }
        } else {
            for (i in list.indices) {
                canvas.drawText(list[i].getLyrics(), (width / 2).toFloat(), (height / 2 + 80 * i).toFloat(), gPaint)
            }
            val highLineLrc: String = list[currentPosition].getLyrics()
            val highLineWidth = gPaint.measureText(highLineLrc).toInt()
            val leftOffset = (width - highLineWidth) / 2
            val lrcBean: LyricsData = list[currentPosition]
            val start: Long = lrcBean.getStart()
            val end: Long = lrcBean.getEnd()
            val i = ((currentMillis - start) * 1.0f / (end - start) * highLineWidth).toInt()
            if (i > 0) {
                val textBitmap = Bitmap.createBitmap(i, 100, Bitmap.Config.ARGB_8888)
                val textCanvas = Canvas(textBitmap)
                textCanvas.drawText(highLineLrc, highLineWidth / 2.toFloat(), 80f, hPaint)
                canvas.drawBitmap(textBitmap, leftOffset.toFloat(), (height / 2 + 80 * (currentPosition - 1)).toFloat(), null)
            }
        }
    }

    fun init() {
        currentPosition = 0
        lastPosition = 0
        scrollY = 0
        invalidate()
    }

    fun setCurrentMill(mills: Long) {
        currentMillis = mills
    }

    private fun getCurrentPosition() {
        try {
            if (currentMillis < list[0].getStart()) {
                currentPosition = 0
                return
            }
            if (currentMillis > list[list.size - 1].getStart()) {
                currentPosition = list.size - 1
                return
            }
            for (i in list.indices) {
                if (currentMillis >= list[i].getStart() && currentMillis < list[i].getEnd()) {
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