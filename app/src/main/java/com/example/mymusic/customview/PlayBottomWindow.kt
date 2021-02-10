package com.example.mymusic.customview

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import com.example.mymusic.R
import com.example.mymusic.play.event.PauseEvent
import com.example.mymusic.play.event.RestartEvent
import com.lzx.starrysky.SongInfo
import kotlinx.android.synthetic.main.window_bottom_play.view.*
import org.greenrobot.eventbus.EventBus

class PlayBottomWindow(var context: Context, builder: ConfirmPopupWindowBuilder? = null) : PopupWindow() {

    private var controlImage: ImageView
    private var isPause = false
    private var isPlaying = false

    init {
        val inflater = LayoutInflater.from(context)
        this.contentView = inflater.inflate(R.layout.window_bottom_play, null) //布局xml
        this.width = LinearLayout.LayoutParams.MATCH_PARENT //父布局减去padding
        this.height = LinearLayout.LayoutParams.WRAP_CONTENT
        this.isOutsideTouchable = false //是否可以
        this.isClippingEnabled = false //背景透明化可以铺满全屏
        controlImage = this.contentView.findViewById(R.id.bottom_bar_image)
        this.contentView.bottom_bar_btn.setOnClickListener {
            if (isPlaying) {
                if (isPause) {
                    EventBus.getDefault().post(RestartEvent())
                } else {
                    EventBus.getDefault().post(PauseEvent())
                }
            }
        }
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

    fun pause() {
        isPause = true
        this.contentView.bottom_bar_btn.setImageResource(R.drawable.pause_icon)
    }

    fun restart() {
        isPause = false
        this.contentView.bottom_bar_btn.setImageResource(R.drawable.play_icon)
    }

    fun setIsPlay(isPlay: Boolean) {
        isPlaying = isPlay
    }

    fun switchSong(songInfo: SongInfo) {
        this.contentView.bottom_bar_author.text = songInfo.artist
        this.contentView.bottom_bar_name.text = songInfo.songName
        if (songInfo.songCover.isNotEmpty()){
            Glide.with(context).load(songInfo.songCover).into(this.controlImage)
        }
        isPause = false
        isPlaying = true
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
