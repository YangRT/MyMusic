package com.example.mymusic.find.ui.hotradio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymusic.MyApplication
import com.example.mymusic.R
import com.example.mymusic.databinding.ItemHotRadioBinding
import com.example.mymusic.databinding.ItemNewMusicBinding
import com.example.mymusic.find.model.HotRadio
import com.example.mymusic.find.model.NewMusicData

class HotRadioAdapter(val list:MutableList<HotRadio>): RecyclerView.Adapter<HotRadioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemHotRadioBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_hot_radio,parent,
            false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = "🎵 ${list[position].playCount/10000}万"
        holder.binding.tvHotRadio.text = text
        Glide.with(MyApplication.context).load(list[position].picUrl).into(holder.itemView.findViewById(R.id.image_hot_radio))
    }

    fun updateData(data: List<HotRadio>) {
        list.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding: ItemHotRadioBinding): RecyclerView.ViewHolder(binding.root) {

    }

}