package com.example.mymusic.rank.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityAllRankBinding
import com.example.mymusic.rank.model.RankInfo
import com.example.mymusic.search.ui.SearchActivity

class AllRankActivity : BaseActivity() {

    private lateinit var binding: ActivityAllRankBinding
    private lateinit var viewModel: AllRankViewModel
    private val list = ArrayList<RankInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_all_rank)
        binding.lifecycleOwner = this
        viewModel =  ViewModelProvider(this).get(AllRankViewModel::class.java)
        initView()

        viewModel.data.observe(this, Observer {
            (binding.allRankRecyclerView.adapter as AllRankAdapter).setList(it)
        })
        viewModel.getCacheData()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarRankAll)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "排行榜"

        binding.allRankRecyclerView.adapter = AllRankAdapter(list)
        val manager = LinearLayoutManager(this)
        binding.allRankRecyclerView.layoutManager = manager
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