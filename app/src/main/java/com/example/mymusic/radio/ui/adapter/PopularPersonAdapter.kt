package com.example.mymusic.radio.ui.adapter

import android.graphics.Color
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.radio.model.PopularPerson

class PopularPersonAdapter(data: MutableList<PopularPerson>) :
    BaseQuickAdapter<PopularPerson, BaseViewHolder>(R.layout.item_radio_person, data) {

    override fun convert(holder: BaseViewHolder, item: PopularPerson) {
        if (holder.layoutPosition < 3 || holder.layoutPosition == 99) {
            holder.setTextColor(R.id.radio_person_no, Color.RED)
        }else {
            holder.setTextColor(R.id.radio_person_no, Color.BLACK)
        }
        if (holder.layoutPosition == 99) {
            holder.setText(R.id.radio_person_no, "⬆️")
        } else {
            holder.setText(R.id.radio_person_no, "${holder.layoutPosition + 1}")
        }
        holder.setText(R.id.radio_person_title, item.nickName)
        holder.setText(R.id.radio_person_author, "粉丝：${item.userFollowedCount}")
        Glide.with(context).load(item.avatarUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.radio_person_image))
    }
}