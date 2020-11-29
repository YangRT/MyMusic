package com.example.mymusic.customview.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.R
import com.example.mymusic.databinding.ItemNewMusicBinding
import com.example.mymusic.databinding.ItemSearchHotWordBinding
import com.example.mymusic.find.model.NewMusicData
import com.example.mymusic.find.ui.newmusic.NewMusicAdapter
import com.example.mymusic.search.model.HotWord


/**
 * @program: MyMusic
 *
 * @description: 热搜榜
 *
 * @author: YangRT
 *
 * @create: 2020-11-29 21:54
 **/

class HotWordAdapter(val list:MutableList<HotWord>): RecyclerView.Adapter<HotWordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemSearchHotWordBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_search_hot_word,parent,
            false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = "${position+1}. ${list[position].first}"
        holder.binding.tvHotWord.text = text
    }

    fun updateData(data: List<HotWord>) {
        list.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding: ItemSearchHotWordBinding): RecyclerView.ViewHolder(binding.root) {

    }
}