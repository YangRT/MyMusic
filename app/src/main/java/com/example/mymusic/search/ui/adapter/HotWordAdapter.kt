package com.example.mymusic.search.ui.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.databinding.ItemSearchHotWordBinding
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

class HotWordAdapter(data:MutableList<HotWord>): BaseQuickAdapter<HotWord, BaseDataBindingHolder<ItemSearchHotWordBinding>>(R.layout.item_search_hot_word,data) {


    override fun convert(holder: BaseDataBindingHolder<ItemSearchHotWordBinding>, item: HotWord) {
        val position = getItemPosition(item)
        val text = SpannableString("${position+1}. ${data[position].first}")
        if (position < 3){
            text.setSpan(ForegroundColorSpan(Color.RED),0,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            holder.dataBinding?.tvHotWord?.paint?.isFakeBoldText = true
        }
        holder.dataBinding?.tvHotWord?.text = text
    }


}
