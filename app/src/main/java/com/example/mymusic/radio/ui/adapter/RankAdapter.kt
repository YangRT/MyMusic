package com.example.mymusic.radio.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.radio.model.Rank

class RankAdapter(data: MutableList<Rank>) :
    BaseQuickAdapter<Rank, BaseViewHolder>(R.layout.item_radio_rank , data) {

    override fun convert(holder: BaseViewHolder, item: Rank) {
        holder.setText(R.id.radio_rank_name, item.name)
        holder.setBackgroundResource(R.id.radio_rank_name, item.picId)
    }
}