package com.example.mymusic.rank.ui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.rank.model.RankInfo

class AllRankAdapter(data: MutableList<RankInfo>) :
    BaseQuickAdapter<RankInfo, BaseViewHolder>(R.layout.item_rank_info , data) {

    override fun convert(holder: BaseViewHolder, item: RankInfo) {
        TODO("Not yet implemented")
    }
}