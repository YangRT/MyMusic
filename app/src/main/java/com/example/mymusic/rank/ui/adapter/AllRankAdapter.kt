package com.example.mymusic.rank.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.rank.model.RankInfo

class AllRankAdapter(data: MutableList<RankInfo>) :
    BaseQuickAdapter<RankInfo, BaseViewHolder>(R.layout.item_rank_info , data) {

    override fun convert(holder: BaseViewHolder, item: RankInfo) {
        holder.setText(R.id.tv_rank_name, item.name)
        holder.setText(R.id.tv_update_frequency, item.updateFrequency)
        holder.setText(R.id.tv_rank_description, item.description)
        Glide.with(context).load(item.coverImgUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.image_rank_info))
    }
}