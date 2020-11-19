package com.example.mymusic.customview.search

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

class SearchHistoryView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int = 0):
    ListView(context,attributeSet,defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE.shr(2),MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

}