package com.example.mymusic.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import android.widget.TextView

// 多条件选择布局（单选）
class TypeGridLayout<T> @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val builder: Builder<T>? = null
) :
    GridLayout(context, attributeSet, defStyleAttr) {


    private val selectedMap = HashMap<String,List<T>>()
    private val itemMap = HashMap<T,String>()
    private val viewMap = HashMap<T,TextView>()


    init {
        builder?.let {
            initAttr()
            initData()
            initView()
            initLayout()
        }
    }

    private fun initAttr() {
        builder?.let {
            this.layoutParams = it.lp
            this.rowCount = it.rowCount
            this.columnCount = it.columnCount
        }
    }

    private fun initData() {

        builder?.let { builder ->
            for (key in builder.data.keys){
                builder.data[key]?.forEach {
                    itemMap[it] = key
                }
            }
        }
    }

    private fun initView() {
        builder?.let {
            itemMap.keys.iterator().forEach {item ->
                val view = TextView(context)
                if (it.textColor != -1) {
                    view.setTextColor(it.textColor)
                }
                if (it.backgroundDrawable != -1) {
                    view.setBackgroundResource(it.backgroundDrawable)
                }
                viewMap[item] = view
            }
        }
    }


    private fun initLayout() {
    }

    class Builder<T>(var context: Context, var data: HashMap<String, List<T>>) {
        var lp: GridLayout.LayoutParams? = null
        var rowCount: Int = 1
        var columnCount: Int = 1
        var textColor: Int = -1
        var textSelectColor: Int = -1
        var backgroundDrawable: Int = -1
        var backgroundSelectedDrawable: Int = -1
        var isSingeSelect: Boolean = true

        fun build(): TypeGridLayout<T> {
            return TypeGridLayout(context, builder = this)
        }
    }

    class ItemClickLsn(val position: Int, val category: String, val text: String) :
        OnClickListener {

        override fun onClick(v: View?) {

        }

    }

}