package com.example.mymusic.search.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.search.model.SearchArtist


/**
 * @program: MyMusic
 *
 * @description: 搜索歌手adapter
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 22:56
 **/

class SearchSingerAdapter(data: MutableList<SearchArtist>) :
    BaseQuickAdapter<SearchArtist, BaseViewHolder>(R.layout.item_search_singer , data) {

    override fun convert(holder: BaseViewHolder, item: SearchArtist) {
        Glide.with(context).load(item.picUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.search_singer_pic))
        holder.setText(R.id.search_singer_name, item.picUrl)
    }
}