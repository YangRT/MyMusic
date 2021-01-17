package com.example.mymusic.search.ui.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.base.PageStatus
import com.example.mymusic.base.model.BaseListFragment
import com.example.mymusic.search.model.SearchByLyricsSong
import com.example.mymusic.search.ui.adapter.SearchLyricsAdapter
import com.example.mymusic.search.ui.viewmodel.SearchLyricsViewModel

class SearchLyricsFragment: BaseListFragment() {

    private lateinit var viewModel: SearchLyricsViewModel
    private lateinit var adapter: SearchLyricsAdapter
    private var list: ArrayList<SearchByLyricsSong> = ArrayList()

    override fun refresh() {
        viewModel.refresh()
    }

    override fun initView() {
        arguments?.let {
            val word = it.getString("search_word","")
            viewModel = ViewModelProvider(
                this,
                SearchLyricsViewModel.ViewModeFactory(word)
            )[SearchLyricsViewModel::class.java]

            binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
            adapter = SearchLyricsAdapter(list)

            viewModel.data.observe(this, Observer {
                adapter.setList(it)
                if (binding.mainPageRefreshLayout.isRefreshing) {
                    binding.mainPageRefreshLayout.isRefreshing = false
                }
            })
            viewModel.status.observe(this, Observer {
                when(it) {
                  PageStatus.EMPTY -> statusHelper.showEmpty()
                  PageStatus.LOADING -> statusHelper.showLoading()
                  PageStatus.SHOW_CONTENT -> statusHelper.showSuccess()
                  else -> statusHelper.showFailed()
                }
            })
            binding.mainPageRefreshLayout.setOnRefreshListener {
                refresh()
            }
        }
    }

    public fun search(text: String) {
        viewModel.word = text
        viewModel.search()
    }
}