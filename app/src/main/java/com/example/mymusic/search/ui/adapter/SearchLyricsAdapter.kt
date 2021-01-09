package com.example.mymusic.search.ui.adapter

import android.os.Build
import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.search.model.SearchByLyricsSong


/**
 * @program: MyMusic
 *
 * @description: 搜索歌词adapter
 *
 * @author: YangRT
 *
 * @create: 2021-01-09 22:57
 **/

class SearchLyricsAdapter(data: MutableList<SearchByLyricsSong>) :
    BaseQuickAdapter<SearchByLyricsSong, BaseViewHolder>(R.layout.item_search_lyrics , data) {

    override fun convert(holder: BaseViewHolder, item: SearchByLyricsSong) {
        holder.setText(R.id.search_lyrics_title, item.name)
        if (item.ar.isNotEmpty()) {
            holder.setText(R.id.search_lyrics_author, item.ar[0].name)
        } else {
            holder.setText(R.id.search_lyrics_author, "未知")
        }
        if (item.lyrics.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.setText(R.id.search_lyrics, Html.fromHtml(item.lyrics[0], Html.FROM_HTML_MODE_COMPACT))
            } else {
                holder.setText(R.id.search_lyrics, Html.fromHtml(item.lyrics[0]))
            }
        }
    }
}