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
import com.example.mymusic.databinding.ActivityPlayRecordBinding
import com.example.mymusic.play.db.PlayedSongInfo

class PlayRecordActivity : BaseActivity() {

    private lateinit var binding: ActivityPlayRecordBinding
    private lateinit var viewModel: PlayRecordViewModel
    private lateinit var adapter: PlayRecordAdapter
    private var list = ArrayList<PlayedSongInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_record)
        viewModel =  ViewModelProvider(this).get(PlayRecordViewModel::class.java)
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarPlayRecord)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "播放历史"

        list = viewModel.getRecordList()
        adapter = PlayRecordAdapter(list)
        binding.playRecordRecyclerView.adapter = adapter
        binding.playRecordRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setEmptyView(R.layout.status_record_empty)

        binding.platRecordRefreshLayout.setOnRefreshListener {
            list = viewModel.getRecordList()
            adapter.setList(list)
            binding.platRecordRefreshLayout.isRefreshing = false
            Toast.makeText(this, R.string.refresh_success, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish() }
        }
        return true
    }
}