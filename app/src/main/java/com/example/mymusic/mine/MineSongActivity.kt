package com.example.mymusic.mine

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityMineSongBinding
import com.example.mymusic.play.PlayController
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky

class MineSongActivity : BaseActivity() {

    private lateinit var binding: ActivityMineSongBinding
    private lateinit var viewModel: MineMusicViewModel
    private lateinit var adapter: MineMusicAdapter
    private var list = ArrayList<SongInfo>()
    private var type: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mine_song)
        viewModel =  ViewModelProvider(this).get(MineMusicViewModel::class.java)
        type = intent.getIntExtra("type", -1)
        if (type < 0) {
            finish()
        }
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarPlayRecord)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        if (type == 0) {
            binding.toolbarTitle.setText(R.string.play_record)
        } else if (type == 1) {
            binding.toolbarTitle.setText(R.string.local_music)
        }
        list = getData()
        adapter = MineMusicAdapter(list)
        binding.playRecordRecyclerView.adapter = adapter
        binding.playRecordRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setEmptyView(R.layout.item_empty_view)
        binding.platRecordRefreshLayout.setOnRefreshListener {
            list = getData()
            adapter.setList(list)
            binding.platRecordRefreshLayout.isRefreshing = false
            Toast.makeText(this, R.string.refresh_success, Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            val info = list[position]
            PlayController.playNow(info)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish() }
        }
        return true
    }

    private fun getData(): ArrayList<SongInfo> {
        if (type == 0) {
            return viewModel.getRecordList()
        } else if (type == 1) {
            return ArrayList(StarrySky.with().querySongInfoInLocal(this))
        }
        return ArrayList()
    }
}