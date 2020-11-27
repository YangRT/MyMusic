package com.example.mymusic.find.ui.hotradio

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.databinding.ItemHotRadioBinding
import com.example.mymusic.find.model.HotRadio

class HotRadioAdapter(val list:MutableList<HotRadio>): RecyclerView.Adapter<HotRadioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(val binding: ItemHotRadioBinding): RecyclerView.ViewHolder(binding.root) {

    }

}