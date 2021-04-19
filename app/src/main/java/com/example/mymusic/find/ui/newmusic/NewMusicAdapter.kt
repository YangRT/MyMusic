package com.example.mymusic.find.ui.newmusic

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.MyApplication
import com.example.mymusic.R
import com.example.mymusic.find.model.NewMusicData

class NewMusicAdapter(data: MutableList<NewMusicData>) :
    BaseQuickAdapter<NewMusicData, BaseViewHolder>(R.layout.item_new_music , data)  {

    override fun convert(holder: BaseViewHolder, item: NewMusicData) {
        holder.setText(R.id.new_music_name, item.song.name)
        holder.setText(R.id.new_music_author, item.song.artists[0].name)
        Glide.with(context).load(item.picUrl).dontAnimate().into(holder.itemView.findViewById(R.id.new_music_pic))
    }
}