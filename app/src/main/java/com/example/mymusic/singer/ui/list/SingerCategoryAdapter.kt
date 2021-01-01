package com.example.mymusic.singer.ui.list

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.mymusic.R
import com.example.mymusic.singer.model.SingerCategory

class SingerCategoryAdapter(data: MutableList<SingerCategory>) :
    BaseQuickAdapter<SingerCategory, BaseViewHolder>(R.layout.item_singer_category , data){

    override fun convert(holder: BaseViewHolder, item: SingerCategory) {
        if (item.isSelect){
            holder.setBackgroundResource(R.id.category_item_tv,R.drawable.shape_category_selected)
            holder.setTextColor(R.id.category_item_tv,Color.WHITE)
        }else {
            holder.setBackgroundResource(R.id.category_item_tv,R.drawable.shape_category_normal)
            holder.setTextColor(R.id.category_item_tv,Color.BLACK)
        }
        holder.setText(R.id.category_item_tv,item.name)
    }

}