package com.example.mymusic.radio.ui.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mymusic.R
import com.example.mymusic.radio.model.RadioBanner
import com.zhpan.bannerview.holder.ViewHolder

class RadioBannerAdapter : ViewHolder<RadioBanner> {

    override fun getLayoutId(): Int {
        return R.layout.item_find_banner
    }

    override fun onBind(itemView: View?, data: RadioBanner?, position: Int, size: Int) {
        val imageView = itemView?.findViewById(R.id.banner_image) as ImageView
        data?.let {
            Glide.with(itemView.context).load(it.pic).into(imageView)
        }
    }
}