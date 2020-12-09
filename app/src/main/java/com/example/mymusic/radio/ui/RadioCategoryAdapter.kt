package com.example.mymusic.radio.ui

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.radio.model.RadioCategory

class RadioCategoryAdapter(data: MutableList<RadioCategory>) :
    BaseQuickAdapter<RadioCategory, BaseViewHolder>(R.layout.item_radio_category , data){

    override fun convert(holder: BaseViewHolder, item: RadioCategory) {
        holder.setText(R.id.tv_radio_category,item.name)
        Glide.with(context).load(item.pic56x56Url).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.image_radio_category))
    }

}