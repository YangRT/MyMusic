package com.example.mymusic.find.ui.banner

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mymusic.R
import com.example.mymusic.find.model.BannerData
import com.zhpan.bannerview.holder.ViewHolder

class BannerAdapter : ViewHolder<BannerData> {

    override fun getLayoutId(): Int {
        return R.layout.find_banner_item
    }

    override fun onBind(itemView: View?, data: BannerData?, position: Int, size: Int) {
        val imageView = itemView?.findViewById(R.id.banner_image) as ImageView
        data?.let {
            Glide.with(itemView.context).load(it.imageUrl).into(imageView)
        }
    }
}