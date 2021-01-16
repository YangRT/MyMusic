package com.example.mymusic.search.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.customview.search.BCallBack
import com.example.mymusic.customview.search.SCallBack
import com.example.mymusic.databinding.ActivitySearchResultBinding
import com.example.mymusic.search.ui.adapter.TabViewPagerAdapter
import com.example.mymusic.search.ui.fragment.*
import com.example.mymusic.search.ui.viewmodel.SearchResultViewModel
import com.example.mymusic.utils.Constants

class SearchResultActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var viewModel: SearchResultViewModel
    private lateinit var adapter: TabViewPagerAdapter
    private var fragments: ArrayList<Fragment> = ArrayList()
    private var category: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search_result)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        val searchWord = intent.getStringExtra("search_word")
        if (searchWord == null) {
            finish()
        }
        viewModel.searchWord.observe(this, Observer {
            binding.searchView.setSearchString(it)
        })
        viewModel.currentIndex.observe(this, Observer {
            binding.searchViewPager.currentItem = it
        })
        viewModel.searchWord.value = searchWord
        initView()
    }

    private fun initView() {

        // 设置搜索框回调
        binding.searchView.setBack(object : BCallBack {
            override fun backAction() {
                finish()
            }
        })
        binding.searchView.setSearchCallBack(object : SCallBack {
            override fun searchAction(text: String) {
                // 搜索
                search(text)
            }
        })
        initCategory()
        initFragment()
        adapter = TabViewPagerAdapter(supportFragmentManager, fragments, category)
        binding.searchViewPager.adapter = adapter
        binding.searchTabLayout.setupWithViewPager(binding.searchViewPager)
    }

    private fun initFragment() {
        // category 与 fragment 顺序一致
        fragments.add(SearchSongFragment())
        fragments.add(SearchSingerFragment())
        fragments.add(SearchSongListFragment())
        fragments.add(SearchRadioFragment())
        fragments.add(SearchLyricsFragment())
    }

    private fun initCategory() {
        // category 与 fragment 顺序一致
        category.add(Constants.SONG)
        category.add(Constants.SINGER)
        category.add(Constants.SONG_LIST)
        category.add(Constants.RADIO)
        category.add(Constants.LYRICS)
    }

    private fun search(text: String) {
        viewModel.searchWord.value = text
        binding.searchViewPager.currentItem = 0
        // 添加搜索记录
        viewModel.insertData(text)
        // 重置fragment搜索词
        for(i in 0 until fragments.size) {
            fragments[i].onResume()
        }
    }
}