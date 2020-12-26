package com.example.mymusic.songlist.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.databinding.ActivityCategoryListBinding
import com.example.mymusic.songlist.model.CategoryList

class CategoryListActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCategoryListBinding
    private lateinit var viewModel: CategoryListViewModel
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

        })
        viewModel.getCacheData()
    }

    private fun initView() {

    }
}