package com.example.mymusic.singer.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.model.Artist
import com.example.mymusic.databinding.ActivitySingerListBinding
import com.example.mymusic.search.ui.SearchActivity
import com.example.mymusic.singer.model.SingerCategory

class SingerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingerListBinding
    private lateinit var viewModel: SingerListViewModel
    private lateinit var adapter: SingerListAdapter
    private lateinit var categoryAdapter: SingerCategoryAdapter
    private val list: ArrayList<Artist> = ArrayList()
    private val categoryList: ArrayList<SingerCategory> = ArrayList()
    private var type = -1
    private var area = -1
    private val TYPE = "type"
    private val AREA = "area"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singer_list)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_singer_list)
        binding.lifecycleOwner = this
        viewModel =  ViewModelProvider(this).get(SingerListViewModel::class.java)

        // 改变状态栏颜色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(android.R.color.transparent)
        }
        setAndroidNativeLightStatusBar()
        initCategory()
        initView()
    }

    private fun initView() {
        categoryAdapter = SingerCategoryAdapter(categoryList)
        categoryAdapter.setOnItemClickListener { adapter, view, position ->

            if (categoryList[position].category == TYPE && categoryList[position].id != type){
                for (i in 0 .. 4){
                    if (categoryList[i].id == type){
                        categoryList[i].isSelect = false
                        break
                    }
                }
                type = categoryList[position].id
                categoryList[position].isSelect = true
                adapter.notifyDataSetChanged()
                viewModel.changeParam(type, area)
                return@setOnItemClickListener
            }
            if (categoryList[position].category == AREA && categoryList[position].id != area){
                for (i in 4 .. 10){
                    if (categoryList[i].id == area){
                        categoryList[i].isSelect = false
                        break
                    }
                }
                area = categoryList[position].id
                categoryList[position].isSelect = true
                adapter.notifyDataSetChanged()
                viewModel.changeParam(type, area)
                return@setOnItemClickListener
            }
        }
        val gridLayoutManager = GridLayoutManager(this, 4)
        binding.recyclerViewSingerCategory.layoutManager = gridLayoutManager
        binding.recyclerViewSingerCategory.adapter = categoryAdapter
        adapter = SingerListAdapter(list)
        adapter.loadMoreModule.isAutoLoadMore = false
        adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        adapter.loadMoreModule.enableLoadMoreEndClick = true
        val manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyclerViewSingerList.layoutManager  = manager
        binding.recyclerViewSingerList.adapter = adapter
        adapter.setOnItemChildClickListener { adapter, view, position ->

        }
        adapter.animationEnable = true
        adapter.setEmptyView(R.layout.status_empty)
        adapter.loadMoreModule.setOnLoadMoreListener {
            viewModel.loadNextPage()
        }
        viewModel.data.observe(this, Observer {
            adapter.setList(it)
        })
        viewModel.getCacheData()

        binding.toolbarSingerList.setNavigationIcon(R.drawable.find_search)
        setSupportActionBar(binding.toolbarSingerList)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "歌手分类"
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

    private fun initCategory() {
        categoryList.add(SingerCategory(TYPE,-1,"全部", true))
        categoryList.add(SingerCategory(TYPE,1,"男生", false))
        categoryList.add(SingerCategory(TYPE,2,"女生", false))
        categoryList.add(SingerCategory(TYPE,3,"组合", false))
        categoryList.add(SingerCategory(AREA,-1,"全部", true))
        categoryList.add(SingerCategory(AREA,7,"华语",false))
        categoryList.add(SingerCategory(AREA,96,"欧美", false))
        categoryList.add(SingerCategory(AREA,8,"日本", false))
        categoryList.add(SingerCategory(AREA,16,"韩国", false))
        categoryList.add(SingerCategory(AREA,0,"其他", false))
    }

    // 改变状态栏字体颜色
    private fun setAndroidNativeLightStatusBar() {
        val decor = this.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}