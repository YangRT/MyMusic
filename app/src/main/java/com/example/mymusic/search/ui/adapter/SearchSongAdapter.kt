package com.example.mymusic.search.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.search.model.SearchSong


/**
 * @program: MyMusic
 *
 * @description: 搜索歌曲adapter
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 22:56
 **/

class SearchSongAdapter(data: MutableList<SearchSong>) :
    BaseQuickAdapter<SearchSong, BaseViewHolder>(R.layout.item_search_song , data) {

    override fun convert(holder: BaseViewHolder, item: SearchSong) {
        holder.setText(R.id.search_song_title, item.name)
        if (item.ar.isNotEmpty()) {
            holder.setText(R.id.search_lyrics_author, item.ar[0].name)
        } else {
            holder.setText(R.id.search_lyrics_author, "未知")
        }
    }
}