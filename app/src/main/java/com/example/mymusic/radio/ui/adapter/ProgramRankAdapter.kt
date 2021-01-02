package com.example.mymusic.radio.ui.adapter

import android.graphics.Color
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.radio.model.TopList

class ProgramRankAdapter (data: MutableList<TopList>) :
    BaseQuickAdapter<TopList, BaseViewHolder>(R.layout.item_radio_program, data) {

    override fun convert(holder: BaseViewHolder, item: TopList) {
        if (holder.layoutPosition < 3 || holder.layoutPosition == 99) {
            holder.setTextColor(R.id.radio_program_no, Color.RED)
        }else {
            holder.setTextColor(R.id.radio_program_no, Color.BLACK)
        }
        if (holder.layoutPosition == 99) {
            holder.setText(R.id.radio_program_no, "⬆️")
        } else {
            holder.setText(R.id.radio_program_no, "${holder.layoutPosition + 1}")
        }
        holder.setText(R.id.radio_program_title, item.program.name)
        holder.setText(R.id.radio_program_author, item.program.description)
        Glide.with(context).load(item.program.coverUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.radio_program_image))
    }
}