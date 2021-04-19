package com.example.mymusic.mine.ui

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.songlist.model.CategoryList

class CollectPlayListAdapter(data: MutableList<CategoryList>):
        BaseQuickAdapter<CategoryList, BaseViewHolder>(R.layout.item_collect_play_list, data) {

    override fun convert(holder: BaseViewHolder, item: CategoryList) {
        holder.setText(R.id.play_list_collect_title, item.name)
        holder.setText(R.id.play_list_collect_author, item.creator.nickname)
        Glide.with(context).load(item.coverImgUrl).into(holder.itemView.findViewById(R.id.play_list_collect_image))
    }
}