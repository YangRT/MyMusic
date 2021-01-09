package com.example.mymusic.search.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R

/**
 * @program: MyMusic
 *
 * @description: 搜索记录 adapter
 *
 * @author: YangRT
 *
 * @create: 2020-11-30 22:40
 **/

class SearchWordAdapter(data:MutableList<String>): BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_history_word,data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.history_item_tv,item)
    }


}