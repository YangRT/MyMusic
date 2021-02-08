package com.example.mymusic.mine

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.play.db.PlayedSongInfo

class PlayRecordAdapter(data: MutableList<PlayedSongInfo>) :
    BaseQuickAdapter<PlayedSongInfo, BaseViewHolder>(R.layout.item_search_song , data) {

    override fun convert(holder: BaseViewHolder, item: PlayedSongInfo) {
        holder.setText(R.id.search_song_title, item.name)
        if (item.artist.isNotEmpty()) {
            holder.setText(R.id.search_song_author, item.artist)
        } else {
            holder.setText(R.id.search_song_author, "未知")
        }
    }
}