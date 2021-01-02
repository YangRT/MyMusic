package com.example.mymusic.radio.ui.adapter

import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.radio.model.DjRadio

class RadioListAdapter(data: MutableList<DjRadio>) :
    BaseQuickAdapter<DjRadio, BaseViewHolder>(R.layout.item_radio_list, data), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: DjRadio) {
        holder.setText(R.id.tv_radio_list_category, item.category)
        holder.setText(R.id.tv_radio_list_name, item.name)
        //holder.itemView.findViewById<TextView>(R.id.tv_radio_list_name).paint.isFakeBoldText = true
        val text = if (item.subCount > 10000) {
            "🎵${item.subCount / 10000}万"
        } else {
            "🎵${item.subCount}"
        }
        holder.setText(R.id.tv_radio_list_count, text)
        if (item.picUrl != null) {
            Glide.with(context).load(item.picUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.radio_category_list_image))
        }

    }
}