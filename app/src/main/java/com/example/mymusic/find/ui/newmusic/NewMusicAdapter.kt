package com.example.mymusic.find.ui.newmusic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymusic.MyApplication
import com.example.mymusic.R
import com.example.mymusic.databinding.ItemNewMusicBinding
import com.example.mymusic.find.model.NewMusicData

class NewMusicAdapter(val list:MutableList<NewMusicData>): RecyclerView.Adapter<NewMusicAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemNewMusicBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_new_music,parent,
                false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.newMusicAuthor.text = list[position].song.artists[0].name
        holder.binding.newMusicName.text = list[position].song.name
        Glide.with(MyApplication.context).load(list[position].picUrl).into(holder.itemView.findViewById(R.id.new_music_pic))
    }

    fun updateData(data: List<NewMusicData>) {
        list.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding: ItemNewMusicBinding): RecyclerView.ViewHolder(binding.root) {

    }
}