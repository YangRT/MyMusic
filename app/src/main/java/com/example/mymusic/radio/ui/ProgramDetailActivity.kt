package com.example.mymusic.radio.ui

import android.content.Intent
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.customview.PlayAnimManager
import com.example.mymusic.databinding.ActivityProgramDetailBinding
import com.example.mymusic.play.PlayController
import com.example.mymusic.play.PlayMusicActivity
import com.example.mymusic.radio.model.Program
import com.example.mymusic.radio.ui.adapter.ProgramDetailAdapter
import com.example.mymusic.radio.ui.viewmodel.ProgramDetailViewModel
import com.example.mymusic.search.ui.SearchActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.lzx.starrysky.SongInfo

class ProgramDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityProgramDetailBinding
    private lateinit var viewModel: ProgramDetailViewModel
    private lateinit var adapter: ProgramDetailAdapter
    private var dataList = ArrayList<Program>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_program_detail)
        binding.lifecycleOwner = this
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) {
            finish()
        } else {
            viewModel = ViewModelProvider(
                this,
                ProgramDetailViewModel.ViewModeFactory(id)
            )[ProgramDetailViewModel::class.java]
        }
        initView()
        viewModel.data.observe(this, Observer {
            if (it.size > 0) {
                adapter.setList(it)
                Glide.with(this).load(it[0].coverUrl).placeholder(R.drawable.pic_loading).into(binding.root.findViewById(R.id.detail_image))
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
        binding.detailToolbar.layoutParams = lp

        setSupportActionBar(binding.detailToolbar)
        val name = intent.getStringExtra("name")
        binding.toolbarTitle.text = name
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val manager = LinearLayoutManager(this)
        binding.detailRecyclerView.layoutManager = manager
        adapter = ProgramDetailAdapter(dataList)
        adapter.setEmptyView(R.layout.status_empty)
        binding.detailRecyclerView.adapter = adapter
        binding.detailAppLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.detailToolbar.alpha =
                Math.abs(verticalOffset * 1f / binding.detailAppLayout.totalScrollRange)
        })

        adapter.setOnItemClickListener { adapter, view, position ->
            val songInfo = SongInfo()
            songInfo.songName = dataList[position].mainSong.name
            songInfo.songCover = dataList[position].coverUrl
            songInfo.songId = dataList[position].mainSong.id.toString()
            songInfo.duration = dataList[position].mainSong.duration.toLong()
            if (dataList[position].mainSong.artists.isNotEmpty()) {
                songInfo.artist = dataList[position].mainSong.artists[0].name
            } else {
                songInfo.artist = "未知"
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