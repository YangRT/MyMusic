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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityRadioPersonBinding
import com.example.mymusic.radio.model.PopularPerson
import com.example.mymusic.radio.ui.adapter.PopularPersonAdapter
import com.example.mymusic.radio.ui.viewmodel.PopularPersonViewModel
import com.example.mymusic.search.ui.SearchActivity

class RadioPersonActivity : BaseActivity() {

    private lateinit var binding: ActivityRadioPersonBinding
    private lateinit var viewModel: PopularPersonViewModel
    private lateinit var adapter: PopularPersonAdapter
    private val list: ArrayList<PopularPerson> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_radio_person)

        binding.lifecycleOwner = this
        viewModel =  ViewModelProvider(this).get(PopularPersonViewModel::class.java)
        initView()
        viewModel.data.observe(this, Observer {
            adapter.setList(it)
        })
        viewModel.getCacheData()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarRadioPerson)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "主播排行榜"

        adapter = PopularPersonAdapter(list)
        binding.recyclerViewRadioPerson.adapter = adapter
        val manager = LinearLayoutManager(this)
        binding.recyclerViewRadioPerson.layoutManager = manager


        adapter.animationEnable = true
        adapter.setEmptyView(R.layout.status_empty)
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