package com.example.mymusic.singer.ui.list

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.base.model.Artist

class SingerListAdapter(data: MutableList<Artist>) :
    BaseQuickAdapter<Artist, BaseViewHolder>(R.layout.item_singer_list , data),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Artist) {
        holder.setText(R.id.singer_name,item.name)
        Glide.with(context).load(item.picUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.singer_pic))
    }


}