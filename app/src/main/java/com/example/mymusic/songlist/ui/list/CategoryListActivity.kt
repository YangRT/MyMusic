package com.example.mymusic.songlist.ui.list

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
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityCategoryListBinding
import com.example.mymusic.search.ui.SearchActivity
import com.example.mymusic.songlist.model.CategoryList

class CategoryListActivity : BaseActivity() {

    private lateinit var binding:ActivityCategoryListBinding
    private lateinit var viewModel: CategoryListViewModel
    private lateinit var adapter: CategoryListAdapter
    private val list = ArrayList<CategoryList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_category_list)
        binding.lifecycleOwner = this
        val cat = intent.getStringExtra("cat")
        if (cat == null) {
            viewModel = ViewModelProvider(
                this,
                CategoryListViewModel.ViewModeFactory("全部")
            )[CategoryListViewModel::class.java]
        } else {
            viewModel = ViewModelProvider(
                this,
                CategoryListViewModel.ViewModeFactory(cat)
            )[CategoryListViewModel::class.java]
        }
        initView()

        viewModel.data.observe(this, Observer {
            adapter.setList(it)
        })
        viewModel.getCacheData()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarCategoryList)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = viewModel.cat

        val gridLayoutManager = GridLayoutManager(this, 3)
        adapter = CategoryListAdapter(list)
        binding.categoryListRecyclerView.layoutManager = gridLayoutManager
        binding.categoryListRecyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            // 点击歌单
        }
        adapter.loadMoreModule.isAutoLoadMore = false
        adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        adapter.loadMoreModule.enableLoadMoreEndClick = true
        adapter.animationEnable = true
        adapter.setEmptyView(R.layout.status_empty)
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