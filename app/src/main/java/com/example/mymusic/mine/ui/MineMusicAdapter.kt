package com.example.mymusic.mine.ui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.lzx.starrysky.SongInfo

class MineMusicAdapter(data: MutableList<SongInfo>) :
    BaseQuickAdapter<SongInfo, BaseViewHolder>(R.layout.item_search_song , data) {

    override fun convert(holder: BaseViewHolder, item: SongInfo) {
        holder.setText(R.id.search_song_title, item.songName)
        if (item.artist.isNotEmpty()) {
            holder.setText(R.id.search_song_author, item.artist)
        } else {
            holder.setText(R.id.search_song_author, "未知")
        }
    }
}