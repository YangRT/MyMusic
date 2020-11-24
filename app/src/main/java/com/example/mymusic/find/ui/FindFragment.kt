package com.example.mymusic.find.ui

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import com.example.mymusic.R
import com.example.mymusic.base.MutiBaseFragment
import com.example.mymusic.databinding.FragmentListBinding
import com.example.mymusic.find.model.BannerData
import com.example.mymusic.find.model.NewMusicData
import com.example.mymusic.find.model.SongList
import com.example.mymusic.find.ui.banner.BannerAdapter
import com.example.mymusic.find.ui.banner.BannerViewModel
import com.example.mymusic.find.ui.newmusic.NewMusicAdapter
import com.example.mymusic.find.ui.newmusic.NewMusicModel
import com.example.mymusic.find.ui.songlist.SongListAdapter
import com.example.mymusic.find.ui.songlist.SongListViewModel
import com.example.wanandroid.base.BaseItemAdapter
import com.example.wanandroid.base.BaseItemModel
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.bannerview.transform.AccordionTransformer

class FindFragment : MutiBaseFragment<FindViewModel, FragmentListBinding>() {

    private var list: MutableList<BaseItemModel> = ArrayList()
    private lateinit var adapter: BaseItemAdapter

    private lateinit var bannerView: LinearLayout
    private var bannerList: ArrayList<BannerData> = ArrayList()
    private lateinit var bannerViewModel: BannerViewModel
    private lateinit var bannerViewPager: BannerViewPager<BannerData, BannerAdapter>

    private lateinit var songListView: LinearLayout
    private var songList: ArrayList<SongList> = ArrayList()
    private lateinit var songListViewModel: SongListViewModel
    private lateinit var songListRecyclerView: RecyclerView

    private lateinit var tabListView: LinearLayout

    private lateinit var newMusicListView: LinearLayout
    private var newMusicList: ArrayList<NewMusicData> = ArrayList()
    private lateinit var newMusicViewModel: NewMusicModel
    private lateinit var newMusicListRecyclerView: RecyclerView


    override fun initView() {
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainPageRefreshLayout.setOnRefreshListener {
        }
        adapter = BaseItemAdapter(list)
        binding.listRecyclerView.adapter = adapter

        bannerViewModel = ViewModelProvider(this).get(BannerViewModel::class.java)
        songListViewModel = ViewModelProvider(this).get(SongListViewModel::class.java)
        newMusicViewModel = ViewModelProvider(this).get(NewMusicModel::class.java)
        initBanner()
        initSongList()
        initNewMusicList()

        tabListView =
            LayoutInflater.from(context).inflate(R.layout.find_tab_list, null) as LinearLayout

        adapter.addHeaderView(bannerView)
        adapter.addHeaderView(tabListView)
        adapter.addHeaderView(songListView)
        adapter.addHeaderView(newMusicListView)

        bannerViewModel.getCacheData()
        songListViewModel.getCacheData()
        newMusicViewModel.getCacheData()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun viewModel(): FindViewModel {
        return FindViewModel()
    }

    override fun refreshCancel() {
    }

    override fun isRefreshing(): Boolean {
        return false
    }


    private fun initBanner() {
        bannerView =
            LayoutInflater.from(context).inflate(R.layout.find_banner, null) as LinearLayout
        bannerViewPager = bannerView.findViewById(R.id.find_banner)

        bannerViewPager
            .setCanLoop(false)
            .setIndicatorGravity(IndicatorGravity.CENTER)
            .setAutoPlay(true)
            .setPageStyle(PageStyle.MULTI_PAGE_SCALE)
            .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            .setIndicatorWidth(14)
            .setIndicatorMargin(0, 0, 52, 12)
            .setScrollDuration(800)
            .setPageMargin(28)
            .setIndicatorColor(Color.GRAY, Color.BLUE)
            .setHolderCreator { BannerAdapter() }
        bannerViewPager.setPageTransformer(object : AccordionTransformer() {})
        bannerViewModel.data.observe(this, Observer {
            bannerList.clear()
            bannerList.addAll(it)
            bannerViewPager.create(bannerList)
        })
    }

    private fun initSongList() {
        songListView =
            LayoutInflater.from(context).inflate(R.layout.find_song_list, null) as LinearLayout
        songListRecyclerView = songListView.findViewById(R.id.song_list_recycler_view)
        songListRecyclerView.adapter = SongListAdapter(songList)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        songListRecyclerView.layoutManager = manager
        songListViewModel.data.observe(this, Observer {
            Log.e("Main", "${it.size}")
            (songListRecyclerView.adapter as SongListAdapter).updateData(it)
        })
    }

    private fun initNewMusicList() {
        newMusicListView =
            LayoutInflater.from(context).inflate(R.layout.find_new_music_list, null) as LinearLayout
        newMusicListRecyclerView = newMusicListView.findViewById(R.id.new_music_list_recycler_view)
        newMusicListRecyclerView.adapter = NewMusicAdapter(newMusicList)
        val manager = GridLayoutManager(context, 3, LinearLayoutManager.HORIZONTAL, false)
        newMusicListRecyclerView.layoutManager = manager
        // 分页滑动
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(newMusicListRecyclerView)
        newMusicViewModel.data.observe(this, Observer
        {
            Log.e("Main", "${it.size}")
            (newMusicListRecyclerView.adapter as NewMusicAdapter).updateData(it)
        })
    }
}