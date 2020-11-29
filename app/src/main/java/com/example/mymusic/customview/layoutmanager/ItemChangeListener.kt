package com.example.mymusic.customview.layoutmanager

import android.view.View

interface ItemChangeListener {

    fun onItemChange(itemView: View?, position: Int)
}