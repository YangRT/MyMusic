package com.example.mymusic.radio.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.customview.PlayBottomWindow
import com.example.mymusic.databinding.ActivityRadioBinding
import com.example.mymusic.play.event.BeginPlayEvent
import com.example.mymusic.play.event.CanNotPlayEvent
import com.example.mymusic.play.event.PauseFinishEvent
import com.example.mymusic.play.event.RestartFinishEvent
import com.example.mymusic.radio.model.RadioBanner
import com.example.mymusic.radio.model.RadioCategory
import com.example.mymusic.radio.model.Rank
import com.example.mymusic.radio.ui.adapter.RadioBannerAdapter
import com.example.mymusic.radio.ui.adapter.RadioCategoryAdapter
import com.example.mymusic.radio.ui.adapter.RankAdapter
import com.example.mymusic.radio.ui.viewmodel.RadioBannerViewModel
import com.example.mymusic.radio.ui.viewmodel.RadioCategoryViewModel
import com.example.mymusic.search.ui.SearchActivity
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.bannerview.transform.AccordionTransformer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RadioActivity : BaseActivity() {

    private lateinit var binding: ActivityRadioBinding
    private lateinit var categoryViewModel: RadioCategoryViewModel
    private lateinit var bannerViewModel: RadioBannerViewModel
    private lateinit var bannerViewPager: BannerViewPager<RadioBanner, RadioBannerAdapter>
    private val rankList = ArrayList<Rank>()
    private lateinit var bottomWindow: PlayBottomWindow


    private val list = ArrayList<RadioCategory>()
    private var bannerList: ArrayList<RadioBanner> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_radio)
        binding.lifecycleOwner = this
        categoryViewModel =  ViewModelProvider(this).get(RadioCategoryViewModel::class.java)
        bannerViewModel = ViewModelProvider(this).get(RadioBannerViewModel::class.java)
        initView()

        categoryViewModel.data.observe(this , Observer {
            (binding.radioCategoryRecyclerView.adapter as RadioCategoryAdapter).setList(it)
        })
        bannerViewModel.data.observe(this, Observer {
            bannerList.clear()
            bannerList.addAll(it)
            bannerViewPager.create(bannerList)
        })
        categoryViewModel.getCacheData()
        bannerViewModel.getCacheData()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed( {
            val builder = PlayBottomWindow.ConfirmPopupWindowBuilder(this)
            bottomWindow = PlayBottomWindow(this, builder)
            bottomWindow.show()
            EventBus.getDefault().register(this)
        }, 1000)
    }

    private fun initView() {
        initRank()
        setSupportActionBar(binding.toolbarRadioList)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "播单"

        val gridLayoutManager = GridLayoutManager(this, 4)
        binding.radioCategoryRecyclerView.layoutManager = gridLayoutManager
        binding.radioCategoryRecyclerView.adapter =
            RadioCategoryAdapter(list)

        bannerViewPager = binding.root.findViewById(R.id.radio_banner)
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
            .setHolderCreator { RadioBannerAdapter() }
        bannerViewPager.setPageTransformer(object : AccordionTransformer() {})

        binding.radioRankRecyclerView.adapter = RankAdapter(rankList)
        val manager = GridLayoutManager(this, 2)
        binding.radioRankRecyclerView.layoutManager = manager
        (binding.radioRankRecyclerView.adapter as RankAdapter).setOnItemClickListener { adapter, view, position ->
            val intent = if (position == 0) {
                Intent(this, RadioProgramActivity::class.java)
            }else {
                Intent(this, RadioPersonActivity::class.java)
            }
            startActivity(intent)
        }
        (binding.radioCategoryRecyclerView.adapter as RadioCategoryAdapter).setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this, CategoryListActivity::class.java)
            intent.putExtra("name", list[position].name)
            intent.putExtra("id", list[position].id)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.singer_list_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.singer_list_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> { finish() }
        }
        return true
    }

    private fun initRank() {
        rankList.add(Rank("节目榜", R.drawable.radio_rank_background))
        rankList.add(Rank("主播榜", R.drawable.radio_rank_background_two))
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun pauseFinish(event: PauseFinishEvent) {
        bottomWindow.pause()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reStartFinish(event: RestartFinishEvent) {
        bottomWindow.restart()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun switchSong(event: BeginPlayEvent) {
        bottomWindow.switchSong(event.songInfo)
    }
}