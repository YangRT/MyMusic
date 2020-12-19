package com.example.mymusic.rank.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.base.model.Track

class RankDetailAdapter(data: MutableList<Track>) :
    BaseQuickAdapter<Track, BaseViewHolder>(R.layout.item_rank_detail, data) {

    override fun convert(holder: BaseViewHolder, item: Track) {
        holder.setText(R.id.rank_detail_title, item.name)
        holder.setText(R.id.rank_detail_no, "${holder.layoutPosition + 1}")
        if (item.ar.isNotEmpty()) {
            holder.setText(R.id.rank_detail_author, item.ar[0].name)
        }
    }
}