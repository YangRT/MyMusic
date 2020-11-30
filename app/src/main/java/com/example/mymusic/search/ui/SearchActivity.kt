package com.example.mymusic.search.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymusic.R
import com.example.mymusic.databinding.ActivitySearchBinding
import com.example.mymusic.search.model.HotWord
import com.google.android.flexbox.FlexboxLayoutManager

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private val hotWordList = ArrayList<HotWord>()
    private val historyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        binding.lifecycleOwner = this
        // 改变状态栏颜色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(android.R.color.transparent)
        }
        setAndroidNativeLightStatusBar()

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.data.observe(this, Observer {
            (binding.searchHotWords.adapter as HotWordAdapter).updateData(it)
        })
        initView()
        viewModel.getCacheData()
    }

    private fun initView() {
        val historyLayoutManager =  FlexboxLayoutManager(this)
        binding.searchHistory.layoutManager = historyLayoutManager
        binding.searchHistory.adapter = SearchWordAdapter(historyList)
        val temp = viewModel.queryData("")
        historyList.clear()
        historyList.addAll(temp)
        binding.searchHistory.adapter?.notifyDataSetChanged()

        val hotWordLayoutManager = GridLayoutManager(this,2)
        binding.searchHotWords.layoutManager = hotWordLayoutManager
        binding.searchHotWords.adapter = HotWordAdapter(hotWordList)
    }


    // 改变状态栏字体颜色
    private fun setAndroidNativeLightStatusBar() {
        val decor = this.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}