package com.example.mymusic.find.ui

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mymusic.R
import com.example.mymusic.base.MutiBaseFragment
import com.example.mymusic.customview.layoutmanager.LayoutConfig
import com.example.mymusic.customview.layoutmanager.StackLayoutManager
import com.example.mymusic.databinding.FragmentListBinding
import com.example.mymusic.find.model.BannerData
import com.example.mymusic.find.model.HotRadio
import com.example.mymusic.find.model.NewMusicData
import com.example.mymusic.find.model.SongList
import com.example.mymusic.find.ui.banner.BannerAdapter
import com.example.mymusic.find.ui.banner.BannerViewModel
import com.example.mymusic.find.ui.hotradio.HotRadioAdapter
import com.example.mymusic.find.ui.hotradio.HotRadioViewModel
import com.example.mymusic.find.ui.newmusic.NewMusicAdapter
import com.example.mymusic.find.ui.newmusic.NewMusicModel
import com.example.mymusic.find.ui.songlist.SongListAdapter
import com.example.mymusic.find.ui.songlist.SongListViewModel
import com.example.mymusic.base.BaseItemAdapter
import com.example.mymusic.base.BaseItemModel
import com.example.mymusic.customview.PlayAnimManager
import com.example.mymusic.play.PlayController
import com.example.mymusic.radio.ui.ProgramDetailActivity
import com.example.mymusic.radio.ui.RadioActivity
import com.example.mymusic.rank.ui.AllRankActivity
import com.example.mymusic.singer.ui.list.SingerListActivity
import com.example.mymusic.songlist.ui.DetailActivity
import com.example.mymusic.songlist.ui.category.CategoryActivity
import com.lzx.starrysky.SongInfo
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.bannerview.transform.AccordionTransformer

class FindFragment : MutiBaseFragment<FindViewModel, FragmentListBinding>(), View.OnClickListener {

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

    private lateinit var hotRadioLisView: LinearLayout
    private var hotRadioList: ArrayList<HotRadio> = ArrayList()
    private lateinit var hotRadioViewModel: HotRadioViewModel
    private lateinit var hotRadioRecyclerView: RecyclerView

    private lateinit var bottomView: LinearLayout

    override fun initView() {
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainPageRefreshLayout.setOnRefreshListener {
        }
        adapter = BaseItemAdapter(list)
        binding.listRecyclerView.adapter = adapter
        binding.mainPageRefreshLayout.setOnRefreshListener {
            refresh()
        }

        bannerViewModel = ViewModelProvider(this).get(BannerViewModel::class.java)
        songListViewModel = ViewModelProvider(this).get(SongListViewModel::class.java)
        newMusicViewModel = ViewModelProvider(this).get(NewMusicModel::class.java)
        hotRadioViewModel = ViewModelProvider(this).get(HotRadioViewModel::class.java)
        initBanner()
        initSongList()
        initNewMusicList()
        initHotRadioList()

        tabListView =
            LayoutInflater.from(context).inflate(R.layout.find_tab_list, null) as LinearLayout
        tabListView.findViewById<LinearLayout>(R.id.tab_singer_layout).setOnClickListener(this)
        tabListView.findViewById<LinearLayout>(R.id.tab_song_layout).setOnClickListener(this)
        tabListView.findViewById<LinearLayout>(R.id.tab_rank_layout).setOnClickListener(this)
        tabListView.findViewById<LinearLayout>(R.id.tab_radio_layout).setOnClickListener(this)


        bottomView = LayoutInflater.from(context).inflate(R.layout.find_bottom_view,null) as LinearLayout

        adapter.addHeaderView(bannerView)
        adapter.addHeaderView(tabListView)
        adapter.addHeaderView(songListView)
        adapter.addHeaderView(newMusicListView)
        adapter.addHeaderView(hotRadioLisView)
        adapter.addHeaderView(bottomView)

        bannerViewModel.getCacheData()
        songListViewModel.getCacheData()
        newMusicViewModel.getCacheData()
        hotRadioViewModel.getCacheData()

        bannerViewModel.status.observe(viewLifecycleOwner,this)
        changePageStatus()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun viewModel(): FindViewModel {
        return FindViewModel()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tab_singer_layout -> {
                val intent = Intent(context, SingerListActivity::class.java)
                startActivity(intent)
            }
            R.id.tab_song_layout -> {
                val intent = Intent(context, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.tab_rank_layout -> {
                val intent = Intent(context, AllRankActivity::class.java)
                startActivity(intent)
            }
            R.id.tab_radio_layout -> {
                val intent = Intent(context, RadioActivity::class.java)
                startActivity(intent)
            }
            R.id.song_list_look_more -> {
                val intent = Intent(context, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.hot_radio_look_more -> {
                val intent = Intent(context, RadioActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun refreshCancel() {
        if (binding.mainPageRefreshLayout.isRefreshing){
            binding.mainPageRefreshLayout.isRefreshing = false
        }
    }

    override fun refresh() {
        bannerViewModel.getCacheData()
        songListViewModel.getCacheData()
        newMusicViewModel.getCacheData()
        hotRadioViewModel.getCacheData()
    }

    override fun isRefreshing(): Boolean {
        return binding.mainPageRefreshLayout.isRefreshing
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
            changePageStatus()
        })
    }

    private fun initSongList() {
        songListView =
            LayoutInflater.from(context).inflate(R.layout.find_song_list, null) as LinearLayout
        songListView.findViewById<TextView>(R.id.song_list_look_more).setOnClickListener(this)
        songListRecyclerView = songListView.findViewById(R.id.song_list_recycler_view)
        songListRecyclerView.adapter = SongListAdapter(songList)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        songListRecyclerView.layoutManager = manager
        songListViewModel.data.observe(this, Observer {
            Log.e("Main", "${it.size}")
            (songListRecyclerView.adapter as SongListAdapter).setList(it)
            changePageStatus()
        })
        (songListRecyclerView.adapter as SongListAdapter).setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("id", songList[position].id)
            intent.putExtra("name", songList[position].name)
            startActivity(intent)
        }
        (songListRecyclerView.adapter as SongListAdapter).setEmptyView(R.layout.item_empty_view)
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
            (newMusicListRecyclerView.adapter as NewMusicAdapter).setList(it)
            changePageStatus()
        })
        (newMusicListRecyclerView.adapter as NewMusicAdapter).setOnItemClickListener { adapter, view, position ->
            // 播放
            val song = newMusicList[position]
            val songInfo = SongInfo()
            songInfo.songId = song.song.id.toString()
            songInfo.songName = song.song.name
            songInfo.songCover = song.picUrl
            songInfo.duration = song.song.duration
            if (song.song.artists.isNotEmpty()) {
                songInfo.artist = song.song.artists[0].name
            }
            val start = IntArray(2)
            view.getLocationInWindow(start)
            start[0] += view.width/2
            PlayAnimManager.setAnim(activity!!, start)
            PlayController.playNow(songInfo)
        }

        val btnPlayAll = newMusicListView.findViewById<TextView>(R.id.btn_play_all)
        btnPlayAll.setOnClickListener {
            // 播放
            val songs = ArrayList<SongInfo>()
            for (newSong in newMusicList) {
                val songInfo = SongInfo()
                songInfo.songId = newSong.song.id.toString()
                songInfo.songName = newSong.song.name
                songInfo.songCover = newSong.picUrl
                songInfo.duration = newSong.song.duration
                if (newSong.song.artists.isNotEmpty()) {
                    songInfo.artist = newSong.song.artists[0].name
                }
                songs.add(songInfo)
            }
            val start = IntArray(2)
            it.getLocationInWindow(start)
            start[0] += it.width/2
            PlayAnimManager.setAnim(activity!!, start)
            PlayController.playAll(songs)
        }
    }

    private fun initHotRadioList() {
        hotRadioLisView = LayoutInflater.from(context).inflate(R.layout.find_hot_radio,null) as LinearLayout
        hotRadioLisView.findViewById<TextView>(R.id.hot_radio_look_more).setOnClickListener(this)
        hotRadioRecyclerView = hotRadioLisView.findViewById(R.id.hot_radio_list_recycler_view)
        hotRadioRecyclerView.adapter = HotRadioAdapter(hotRadioList)
        val config = LayoutConfig()
        config.secondaryScale = 0.8f
        config.scaleRatio = 0.4f
        config.maxStackCount = 4
        config.initialStackCount = 2
        config.space = 45
        val manager = StackLayoutManager(config)
        hotRadioRecyclerView.layoutManager = manager
        hotRadioViewModel.data.observe(this, Observer
        {
            Log.e("Main", "${it.size}")
            (hotRadioRecyclerView.adapter as HotRadioAdapter).setList(it)
            changePageStatus()
        })
        (hotRadioRecyclerView.adapter as HotRadioAdapter).setOnItemClickListener { adapter, view, position ->
            // 详情
            val intent = Intent(activity, ProgramDetailActivity::class.java)
            intent.putExtra("id",hotRadioList[position].id)
            intent.putExtra("name",hotRadioList[position].name)
            startActivity(intent)
        }
    }

    private fun changePageStatus() {
        if (bannerList.size == 0 && bannerList.size == 0 && newMusicList.size == 0 && hotRadioList.size == 0) {
            statusHelper.showEmpty()
        }else {
            statusHelper.showSuccess()
        }
    }
}