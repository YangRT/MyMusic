package com.example.mymusic.base.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alguojian.mylibrary.StatusLayout
import com.example.mymusic.R
import com.example.mymusic.base.BaseStatusAdapter
import com.example.mymusic.databinding.FragmentListBinding

abstract class BaseListFragment: Fragment() {

    protected lateinit var binding: FragmentListBinding
    protected lateinit var statusHelper: StatusLayout.StatusHelper

    abstract fun refresh()
    abstract fun initView()
    abstract fun search(text: String)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        retainInstance = true
        val statusLayout = StatusLayout.setNewAdapter(BaseStatusAdapter())
        statusHelper = statusLayout.attachView(binding.root).onRetryClick {
            refresh()
        }
        return StatusLayout.getRootView()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }
}