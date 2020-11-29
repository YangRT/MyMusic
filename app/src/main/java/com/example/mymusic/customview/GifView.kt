package com.example.mymusic.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mymusic.R
import kotlinx.android.synthetic.main.gif_view.view.*


/**
 * @program: MyMusic
 *
 * @description: 播放gif图片
 *
 * @author: YangRT
 *
 * @create: 2020-11-26 23:44
 **/

class GifView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private var resId = -1


    init {
        LayoutInflater.from(context).inflate(R.layout.gif_view,this)
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.GifView)
        resId = typeArray.getResourceId(R.styleable.GifView_res_id,-1)
        typeArray.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (resId != -1){
            Glide.with(context).asGif().load(resId).error(R.drawable.gift).into(this.gif_image)
        }
    }

}