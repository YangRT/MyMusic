package com.example.mymusic.search.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.radio.model.DjRadio


/**
 * @program: MyMusic
 *
 * @description: 搜索播单adapter
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 22:57
 **/

class SearchRadioAdapter(data: MutableList<DjRadio>) :
    BaseQuickAdapter<DjRadio, BaseViewHolder>(R.layout.item_search_radio , data) {
    override fun convert(holder: BaseViewHolder, item: DjRadio) {
        Glide.with(context).load(item.picUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.search_radio_image))
        holder.setText(R.id.search_radio_title, item.name)
        holder.setText(R.id.search_radio_category, item.category)
        val text = if (item.subCount > 10000) {
            "🎵${item.subCount / 10000}万"
        } else {
            "🎵${item.subCount}"
        }
        holder.setText(R.id.search_radio_count, text)
    }
}