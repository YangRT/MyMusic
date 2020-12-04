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
import com.example.mymusic.customview.search.BCallBack
import com.example.mymusic.customview.search.SCallBack
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
            (binding.searchHotWords.adapter as HotWordAdapter).setList(it)
        })
        initView()
        viewModel.getCacheData()
    }

    private fun initView() {
        val historyLayoutManager =  FlexboxLayoutManager(this)
        binding.searchHistory.layoutManager = historyLayoutManager
        binding.searchHistory.adapter = SearchWordAdapter(historyList)
        historyDataChange()

        val hotWordLayoutManager = GridLayoutManager(this,2)
        binding.searchHotWords.layoutManager = hotWordLayoutManager
        binding.searchHotWords.adapter = HotWordAdapter(hotWordList)
        binding.tvHistoryTitle.paint.isFakeBoldText = true
        binding.tvHotWordTitle.paint.isFakeBoldText = true
        binding.searchView.setBack(object : BCallBack{
            override fun backAction() {
                finish()
            }
        })
        binding.searchView.setSearchCallBack(object : SCallBack{
            override fun searchAction(text: String) {
                historyDataChange()
                // 搜索
                search(text)
            }
        })
        binding.imageDeleteHistory.setOnClickListener {
            val result = binding.searchView.deleteData()
            if (result){
                historyDataChange()
            }
        }
        (binding.searchHotWords.adapter as HotWordAdapter).setOnItemClickListener { adapter, view, position ->
            val word = (adapter.getItem(position) as HotWord).first
            search(word)
            if (viewModel.hasData(word)){
                viewModel.deleteItemData(word)
            }
            viewModel.insertData(word)
            historyDataChange()
        }
        (binding.searchHistory.adapter as SearchWordAdapter).setOnItemClickListener { adapter, view, position ->
            val historyWord = (adapter.getItem(position) as String)
            search(historyWord)
            if (viewModel.hasData(historyWord)){
                viewModel.deleteItemData(historyWord)
            }
            viewModel.insertData(historyWord)
            historyDataChange()
        }
    }

    private fun search(word: String) {

    }

    private fun historyDataChange() {
        val t = viewModel.queryData("")
        historyList.clear()
        historyList.addAll(t)
        binding.searchHistory.adapter?.notifyDataSetChanged()
    }


    // 改变状态栏字体颜色
    private fun setAndroidNativeLightStatusBar() {
        val decor = this.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}