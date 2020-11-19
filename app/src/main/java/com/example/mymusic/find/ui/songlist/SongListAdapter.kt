package com.example.mymusic.find.ui.songlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymusic.MyApplication.Companion.context
import com.example.mymusic.R
import com.example.mymusic.databinding.SongListItemBinding
import com.example.mymusic.find.model.SongList

class SongListAdapter(val list:MutableList<SongList>): RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<SongListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.song_list_item,parent,
                false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.songListName.text = list[position].name
        Glide.with(context).load(list[position].picUrl).into(holder.itemView.findViewById(R.id.song_list_image))
    }

    fun updateData(data: List<SongList>) {
        list.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding:SongListItemBinding): RecyclerView.ViewHolder(binding.root) {

    }
}