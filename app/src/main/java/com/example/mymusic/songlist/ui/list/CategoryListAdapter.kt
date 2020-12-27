package com.example.mymusic.songlist.ui.list

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.base.model.Artist
import com.example.mymusic.songlist.model.CategoryList


/**
 * @program: MyMusic
 *
 * @description: 分类列表adapter
 *
 * @author: YangRT
 *
 * @create: 2020-12-26 10:41
 **/

class CategoryListAdapter(data: MutableList<CategoryList>) :
    BaseQuickAdapter<CategoryList, BaseViewHolder>(R.layout.item_song_list_category_list, data),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CategoryList) {
        holder.setText(R.id.category_list_item_name, item.name)
        Glide.with(context).load(item.coverImgUrl).placeholder(R.drawable.pic_loading)
            .into(holder.itemView.findViewById(R.id.category_list_item_image))
        val text = if (item.playCount < 10000) {
            "🎵 ${item.playCount}"
        } else {
            "🎵 ${item.playCount / 10000}万"
        }
        holder.setText(R.id.category_list_item_play, text)
    }
}