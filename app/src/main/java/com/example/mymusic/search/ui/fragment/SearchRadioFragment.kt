package com.example.mymusic.search.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.base.PageStatus
import com.example.mymusic.base.model.BaseListFragment
import com.example.mymusic.radio.model.DjRadio
import com.example.mymusic.search.ui.adapter.SearchRadioAdapter
import com.example.mymusic.search.ui.viewmodel.SearchRadioViewModel

class SearchRadioFragment: BaseListFragment() {

    private lateinit var viewModel: SearchRadioViewModel
    private lateinit var adapter: SearchRadioAdapter
    private var list: ArrayList<DjRadio> = ArrayList()

    override fun refresh() {
        viewModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val word = it.getString("search_word", "")
            viewModel = ViewModelProvider(
                this,
                SearchRadioViewModel.ViewModeFactory(word)
            )[SearchRadioViewModel::class.java]
        }
    }

    override fun initView() {
        if (viewModel.isInit) {
            return
        }
        viewModel.isInit = true
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchRadioAdapter(list)
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
    }

    override fun search(text: String) {
        viewModel.word = text
        viewModel.search()
    }
}