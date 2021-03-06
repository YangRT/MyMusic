package com.example.mymusic.search.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.base.PageStatus
import com.example.mymusic.base.model.BaseListFragment
import com.example.mymusic.customview.PlayAnimManager
import com.example.mymusic.play.PlayController
import com.example.mymusic.play.PlayMusicActivity
import com.example.mymusic.search.model.SearchSong
import com.example.mymusic.search.ui.adapter.SearchSongAdapter
import com.example.mymusic.search.ui.viewmodel.SearchSongViewModel
import com.lzx.starrysky.SongInfo
import org.greenrobot.eventbus.EventBus

class SearchSongFragment: BaseListFragment() {

    private lateinit var viewModel: SearchSongViewModel
    private lateinit var adapter: SearchSongAdapter
    private var list: ArrayList<SearchSong> = ArrayList()

    override fun refresh() {
        viewModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val word = it.getString("search_word", "")
            viewModel = ViewModelProvider(
                this,
                SearchSongViewModel.ViewModeFactory(word)
            )[SearchSongViewModel::class.java]
        }
    }

    override fun initView() {
        if (viewModel.isInit) {
            return
        }
        viewModel.isInit = true
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchSongAdapter(list)
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
            // 播放
            val song = SongInfo()
            song.songId = list[position].id.toString()
            song.songName = list[position].name
            if (list[position].ar.isNotEmpty()) {
                song.artist = list[position].ar[0].name
            } else {
                song.artist = "未知"
            }
            song.duration = list[position].dt
            song.songCover = list[position].al.picUrl
            PlayController.playNow(song)
            val start = IntArray(2)
            view.getLocationInWindow(start)
            start[0] += view.width/2
            PlayAnimManager.setAnim(activity!!, start)
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(activity, PlayMusicActivity::class.java)
                startActivity(intent)
            }, 1500)
        }
    }

    override fun search(text: String) {
        viewModel.word = text
        viewModel.search()
    }
}