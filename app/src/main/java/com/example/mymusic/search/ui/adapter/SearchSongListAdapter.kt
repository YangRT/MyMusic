package com.example.mymusic.search.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.search.model.SearchPlaylist


/**
 * @program: MyMusic
 *
 * @description: 搜索歌单adapter
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 22:58
 **/

class SearchSongListAdapter(data: MutableList<SearchPlaylist>) :
    BaseQuickAdapter<SearchPlaylist, BaseViewHolder>(R.layout.item_search_song_list , data) {

    override fun convert(holder: BaseViewHolder, item: SearchPlaylist) {
        holder.setText(R.id.search_song_list_title, item.name)
        holder.setText(R.id.search_song_list_desc, item.description)
        Glide.with(context).load(item.coverImgUrl).placeholder(R.drawable.pic_loading).into(holder.itemView.findViewById(R.id.search_song_list_image))
    }
}