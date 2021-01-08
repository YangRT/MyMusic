package com.example.mymusic.radio.ui.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.radio.model.Program


/**
 * @program: MyMusic
 *
 * @description: 节目详情adapter
 *
 * @author: YangRT
 *
 * @create: 2021-01-08 22:38
 **/

class ProgramDetailAdapter (data: MutableList<Program>) :
    BaseQuickAdapter<Program, BaseViewHolder>(R.layout.item_rank_detail, data) {

    override fun convert(holder: BaseViewHolder, item: Program) {
        holder.setText(R.id.rank_detail_title, item.name)
        if (holder.layoutPosition < 3) {
            holder.setTextColor(R.id.rank_detail_no, Color.RED)
        }else {
            holder.setTextColor(R.id.rank_detail_no, Color.BLACK)
        }
        holder.setText(R.id.rank_detail_no, "${holder.layoutPosition + 1}")
        if (item.mainSong.artists.isNotEmpty() && item.mainSong.artists[0].name.isNotEmpty()) {
            holder.setText(R.id.rank_detail_author, item.mainSong.artists[0].name)
        } else {
            holder.setText(R.id.rank_detail_author, "未知")
        }
    }
}