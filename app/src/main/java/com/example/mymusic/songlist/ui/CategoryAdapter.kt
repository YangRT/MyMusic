package com.example.mymusic.songlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.R
import com.example.mymusic.databinding.ItemSongListCategoryBinding
import com.example.mymusic.databinding.ItemSongListCategoryTitleBinding
import com.example.mymusic.songlist.model.SongListSubCategory

class CategoryAdapter(val list: ArrayList<SongListSubCategory>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val title = 1
    private val normal = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding = DataBindingUtil.inflate<ItemSongListCategoryTitleBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_song_list_category_title,parent,
                false)
            return TitleViewHolder(binding)
        }else {
            val binding = DataBindingUtil.inflate<ItemSongListCategoryBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_song_list_category,parent,
                false)
            return ViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(data: List<SongListSubCategory>) {
        list.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleViewHolder) {
            holder.binding.songListCategoryTitleTv.text = list[position].name
            holder.binding.songListCategoryTitleTv.paint.isFakeBoldText = true
        }else if (holder is ViewHolder){
            holder.binding.songListCategoryTv.text = list[position].name
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].resourceCount < 0) {
            title
        }else {
            normal
        }
    }

    class ViewHolder(val binding: ItemSongListCategoryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    class TitleViewHolder(val binding: ItemSongListCategoryTitleBinding): RecyclerView.ViewHolder(binding.root) {

    }
}