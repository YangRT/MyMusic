package com.example.mymusic.rank.ui

import android.content.Intent
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.base.model.Track
import com.example.mymusic.customview.PlayAnimManager
import com.example.mymusic.databinding.ActivityRankDetailBinding
import com.example.mymusic.play.PlayController
import com.example.mymusic.play.PlayMusicActivity
import com.example.mymusic.rank.ui.adapter.RankDetailAdapter
import com.example.mymusic.search.ui.SearchActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.lzx.starrysky.SongInfo

class RankDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityRankDetailBinding
    private lateinit var viewModel: RankDetailViewModel
    private lateinit var adapter: RankDetailAdapter
    private lateinit var subscribeTextView: TextView
    private lateinit var commentTextView: TextView
    private lateinit var shareTextView: TextView
    private val dataList = ArrayList<Track>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rank_detail)
        binding.lifecycleOwner = this
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) {
            finish()
        } else {
            viewModel = ViewModelProvider(
                this,
                RankDetailViewModel.ViewModeFactory(id)
            )[RankDetailViewModel::class.java]
        }
        initView()
        viewModel.data.observe(this, Observer {
            if (it.size > 0) {
                adapter.setList(it[0].tracks)
                Glide.with(this).load(it[0].coverImgUrl).placeholder(R.drawable.pic_loading).into(binding.root.findViewById(R.id.rank_detail_image))
                shareTextView.text = it[0].shareCount.toString()
                commentTextView.text = "${it[0].commentCount / 10000} 万"
                subscribeTextView.text = "${it[0].subscribedCount / 10000} 万"
            }
        })
        viewModel.getCacheData()
    }

    private fun initView() {
        val resourceId: Int = this.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight: Int = this.resources.getDimensionPixelSize(resourceId)

        val typedValue = TypedValue()
        this.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
        val attribute = intArrayOf(android.R.attr.actionBarSize)
        val array: TypedArray = this.obtainStyledAttributes(typedValue.resourceId, attribute)
        val viewTop = array.getDimensionPixelSize(0, -1)
        array.recycle()

        val lp = CollapsingToolbarLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, viewTop)
        lp.topMargin = statusBarHeight
        lp.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
        binding.rankDetialToolbar.layoutParams = lp

        setSupportActionBar(binding.rankDetialToolbar)
        val name = intent.getStringExtra("name")
        binding.toolbarTitle.text = name
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        subscribeTextView = binding.layoutInformation.findViewById(R.id.tv_rank_detail_subscribe)
        shareTextView = binding.layoutInformation.findViewById(R.id.tv_rank_detail_share)
        commentTextView = binding.layoutInformation.findViewById(R.id.tv_rank_detail_comment)

        val manager = LinearLayoutManager(this)
        binding.rankRecylerView.layoutManager = manager
        adapter = RankDetailAdapter(dataList)
        binding.rankRecylerView.adapter = adapter
        binding.rankDetialAppLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.rankDetialToolbar.alpha =
                Math.abs(verticalOffset * 1f / binding.rankDetialAppLayout.totalScrollRange)
        })

        adapter.setOnItemClickListener { adapter, view, position ->
            val songInfo = SongInfo()
            songInfo.songName = dataList[position].name
            songInfo.songId = dataList[position].id.toString()
            songInfo.duration = dataList[position].dt
            if (dataList[position].ar.isNotEmpty()) {
                songInfo.artist = dataList[position].ar[0].name
            } else {
                songInfo.artist = "未知"
            }
            if (!dataList[position].al.picUrl.isNullOrEmpty()) {
                songInfo.songCover = dataList[position].al.picUrl
            }
            PlayController.playNow(songInfo)

            val start = IntArray(2)
            view.getLocationInWindow(start)
            start[0] += view.width/2
            PlayAnimManager.setAnim(this, start)
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(this, PlayMusicActivity::class.java)
                startActivity(intent)
            }, 1500)
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
}