package com.example.mymusic.rank.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityRankDetailBinding

class RankDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityRankDetailBinding
    private lateinit var viewModel: RankDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_rank_detail)
        binding.lifecycleOwner = this
        val id = intent.getLongExtra("id",-1L)
        if (id == -1L){
            finish()
        }else {
            viewModel = ViewModelProvider(this, RankDetailViewModel.ViewModeFactory(id))[RankDetailViewModel::class.java]
        }
        initView()

        viewModel.data.observe(this, Observer {

        })
        viewModel.getCacheData()
    }

    private fun initView() {

    }
}