package com.example.mymusic.find.ui.songlist

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.find.model.SongList

class SongListAdapter(data: MutableList<SongList>) :
    BaseQuickAdapter<SongList, BaseViewHolder>(R.layout.item_song_list , data) {


    override fun convert(holder: BaseViewHolder, item: SongList) {
        holder.setText(R.id.song_list_name,item.name)
        Glide.with(context).load(item.picUrl).into(holder.itemView.findViewById(R.id.song_list_image))
    }
}