package com.example.mymusic.search.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.base.PageStatus
import com.example.mymusic.base.model.BaseListFragment
import com.example.mymusic.search.model.SearchArtist
import com.example.mymusic.search.ui.adapter.SearchSingerAdapter
import com.example.mymusic.search.ui.viewmodel.SearchSingerViewModel
import com.example.mymusic.singer.ui.detail.SingerDetailActivity

class SearchSingerFragment: BaseListFragment() {

    private lateinit var viewModel: SearchSingerViewModel
    private lateinit var adapter: SearchSingerAdapter
    private var list: ArrayList<SearchArtist> = ArrayList()

    override fun refresh() {
        viewModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val word = it.getString("search_word", "")
            viewModel = ViewModelProvider(
                this,
                SearchSingerViewModel.ViewModeFactory(word)
            )[SearchSingerViewModel::class.java]
        }
    }

    override fun initView() {
        if (viewModel.isInit) {
            return
        }
        viewModel.isInit = true
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchSingerAdapter(list)
        binding.listRecyclerView.adapter = adapter

        viewModel.data.observe(this, Observer {
            adapter.setList(it)
            if (binding.mainPageRefreshLayout.isRefreshing) {
                binding.mainPageRefreshLayout.isRefreshing = false
            }
        })
        viewModel.status.observe(this, Observer {
            when (it) {
                PageStatus.EMPTY -> statusHelper.showEmpty()
                PageStatus.REFRESH_ERROR -> {
                    if (list.size == 0) {
                        statusHelper.showEmpty()
                    }
                }
                PageStatus.LOADING -> statusHelper.showLoading()
                PageStatus.SHOW_CONTENT -> statusHelper.showSuccess()
                else -> statusHelper.showFailed()
            }
        })
        binding.mainPageRefreshLayout.setOnRefreshListener {
            refresh()
        }
        viewModel.search()

        adapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(context, SingerDetailActivity::class.java)
            intent.putExtra("id", list[position].id)
            intent.putExtra("name", list[position].name)
            startActivity(intent)
        }
    }


    override fun search(text: String) {
        viewModel.word = text
        viewModel.search()
    }
}