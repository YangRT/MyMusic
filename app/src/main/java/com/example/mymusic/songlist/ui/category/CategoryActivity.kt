package com.example.mymusic.songlist.ui.category

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
import com.example.mymusic.databinding.ActivitySongListCategoryBinding
import com.example.mymusic.search.ui.SearchActivity
import com.example.mymusic.songlist.model.SongListSubCategory
import com.example.mymusic.songlist.ui.list.CategoryListActivity

class CategoryActivity : BaseActivity() {

    private lateinit var binding: ActivitySongListCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private val list = ArrayList<SongListSubCategory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_song_list_category)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        initView()
        viewModel.data.observe(this, Observer { arrayList ->
            (binding.songListCategoryRecyclerView.adapter as CategoryAdapter).updateData(arrayList)
        })
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

    private fun initView() {
        setSupportActionBar(binding.toolbarSongList)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarSongTitle.text = "歌单分类"
        val manager = GridLayoutManager(this,3)
        manager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if (list.size > position) {
                    if (list[position].resourceCount < 0) {
                        return 3
                    }
                }
                return 1
            }
        }
        binding.songListCategoryRecyclerView.layoutManager = manager
        binding.songListCategoryRecyclerView.adapter =
            CategoryAdapter(list)
        (binding.songListCategoryRecyclerView.adapter as CategoryAdapter).setItemClickListener(object : CategoryAdapter.CategoryItemClickListener{
            override fun itemClick(item: SongListSubCategory) {
                val intent = Intent(this@CategoryActivity, CategoryListActivity::class.java)
                intent.putExtra("cat", item.name)
                startActivity(intent)
            }

        })
        viewModel.getCacheData()
    }

}