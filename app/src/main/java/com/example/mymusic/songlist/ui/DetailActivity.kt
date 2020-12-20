package com.example.mymusic.songlist.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityDetailBinding

import com.example.mymusic.rank.ui.adapter.RankDetailAdapter

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: RankDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) {
            finish()
        } else {
            viewModel = ViewModelProvider(
                this,
                DetailViewModel.ViewModeFactory(id)
            )[DetailViewModel::class.java]
        }
    }
}