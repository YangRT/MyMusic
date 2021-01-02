package com.example.mymusic.radio.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityRadioCategoryListBinding
import com.example.mymusic.radio.model.DjRadio
import com.example.mymusic.radio.model.PopularPerson
import com.example.mymusic.radio.ui.adapter.PopularPersonAdapter
import com.example.mymusic.radio.ui.adapter.RadioListAdapter
import com.example.mymusic.radio.ui.viewmodel.CategoryListViewModel
import com.example.mymusic.rank.ui.RankDetailViewModel
import com.example.mymusic.search.ui.SearchActivity

class CategoryListActivity : BaseActivity() {

    private lateinit var binding: ActivityRadioCategoryListBinding
    private lateinit var viewModel: CategoryListViewModel
    private lateinit var adapter: RadioListAdapter
    private val list: ArrayList<DjRadio> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_radio_category_list)
        binding.lifecycleOwner = this
        val id = intent.getIntExtra("id", -1)
        if (id == -1) {
            finish()
        } else {
            viewModel = ViewModelProvider(
                this,
                CategoryListViewModel.ViewModeFactory(id)
            )[CategoryListViewModel::class.java]
        }
        initView()
        viewModel.getCacheData()
        viewModel.data.observe(this, Observer {
            adapter.setList(it)
        })
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarRadioCategoryList)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        val name = intent.getStringExtra("name")
        binding.toolbarTitle.text = name

        adapter = RadioListAdapter(list)
        binding.recyclerViewRadioCategoryList.adapter = adapter
        val manager = GridLayoutManager(this, 2)
        binding.recyclerViewRadioCategoryList.layoutManager = manager


        adapter.animationEnable = true
        adapter.setEmptyView(R.layout.status_empty)
        adapter.loadMoreModule.isAutoLoadMore = false
        adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        adapter.loadMoreModule.enableLoadMoreEndClick = true
        adapter.loadMoreModule.setOnLoadMoreListener {
            viewModel.loadNextPage()
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