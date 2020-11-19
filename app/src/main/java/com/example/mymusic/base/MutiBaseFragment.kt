package com.example.mymusic.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class MutiBaseFragment<VM:ViewModel,T: ViewDataBinding>: Fragment(), Observer<Any> {

    protected lateinit var binding:T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        initView()
        return binding.root
    }


    override fun onChanged(t: Any?) {

    }

    abstract fun initView()

    abstract fun getLayoutId():Int

    abstract fun viewModel():VM

    abstract fun refreshCancel()

    abstract fun isRefreshing():Boolean
}