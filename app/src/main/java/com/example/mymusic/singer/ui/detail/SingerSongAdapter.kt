package com.example.mymusic.singer.ui.detail

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.base.model.HotSong

class SingerSongAdapter(data: MutableList<HotSong>) :
    BaseQuickAdapter<HotSong, BaseViewHolder>(R.layout.item_rank_detail, data), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: HotSong) {
        holder.setText(R.id.rank_detail_title, item.name)
        holder.setTextColor(R.id.rank_detail_no, Color.BLACK)
        holder.setText(R.id.rank_detail_no, "${holder.layoutPosition + 1}")
        if (item.ar.isNotEmpty()) {
            if (item.ar.size > 1) {
                holder.setText(R.id.rank_detail_author, "${item.ar[0].name} ${item.ar[1].name}")
            } else {
                holder.setText(R.id.rank_detail_author, item.ar[0].name)
            }
        }
    }
}