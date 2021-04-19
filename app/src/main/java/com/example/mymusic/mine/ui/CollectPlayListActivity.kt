package com.example.mymusic.mine.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityCollectPlayListBinding
import com.example.mymusic.mine.viewmodel.CollectPlayListViewModel
import com.example.mymusic.songlist.model.CategoryList
import com.example.mymusic.songlist.ui.DetailActivity
import com.example.mymusic.utils.Constants
import com.example.mymusic.utils.getSaveData

class CollectPlayListActivity : BaseActivity() {

    private lateinit var binding: ActivityCollectPlayListBinding
    private lateinit var viewModel: CollectPlayListViewModel
    private lateinit var adapter: CollectPlayListAdapter
    private var list = ArrayList<CategoryList>()
    private var useId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collect_play_list)
        viewModel =  ViewModelProvider(this).get(CollectPlayListViewModel::class.java)
        useId = getSaveData(Constants.USER_ID)
        if (useId == null) {
            Toast.makeText(this, "获取用户信息失败！", Toast.LENGTH_SHORT).show()
            finish()
        }
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarCollectPlayList)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.setText(R.string.collect_playlist)

        adapter = CollectPlayListAdapter(list)
        binding.collectPlayListRecyclerView.adapter = adapter
        binding.collectPlayListRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setEmptyView(R.layout.item_empty_view)
        binding.collectPlayListRefreshLayout.setOnRefreshListener {
            // list = getData()
            adapter.setList(list)
            binding.collectPlayListRefreshLayout.isRefreshing = false
            Toast.makeText(this, R.string.refresh_success, Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            val playList = list[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", playList.id)
            intent.putExtra("name", playList.name)
            startActivity(intent)
        }

        viewModel.data.observe(this, Observer {
            adapter.setList(it)
        })
        useId?.let {
            viewModel.getData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish() }
        }
        return true
    }
}