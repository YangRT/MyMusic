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
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityRadioBinding
import com.example.mymusic.radio.model.RadioCategory
import com.example.mymusic.search.ui.SearchActivity

class RadioActivity : BaseActivity() {

    private lateinit var binding: ActivityRadioBinding
    private lateinit var categoryViewModel: RadioCategoryViewModel
    private val list = ArrayList<RadioCategory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_radio)
        binding.lifecycleOwner = this
        categoryViewModel =  ViewModelProvider(this).get(RadioCategoryViewModel::class.java)
        initView()

        categoryViewModel.data.observe(this , Observer {
            (binding.radioCategoryRecyclerView.adapter as RadioCategoryAdapter).setList(it)
        })
        categoryViewModel.getCacheData()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarRadioList)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "播单"

        val gridLayoutManager = GridLayoutManager(this, 4)
        binding.radioCategoryRecyclerView.layoutManager = gridLayoutManager
        binding.radioCategoryRecyclerView.adapter = RadioCategoryAdapter(list)
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