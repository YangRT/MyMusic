package com.example.mymusic.find.ui.hotradio

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.find.model.HotRadio

class HotRadioAdapter(data: MutableList<HotRadio>) :
    BaseQuickAdapter<HotRadio, BaseViewHolder>(R.layout.item_hot_radio , data) {

    override fun convert(holder: BaseViewHolder, item: HotRadio) {
        val text = "🎵 ${item.playCount/10000}万"
        holder.setText(R.id.tv_hot_radio, text)
        Glide.with(context).load(item.picUrl).into(holder.itemView.findViewById(R.id.image_hot_radio))

    }


}