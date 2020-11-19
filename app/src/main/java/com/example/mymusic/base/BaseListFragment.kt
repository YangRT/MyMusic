package com.example.wanandroid.base


import com.example.mymusic.R
import com.example.mymusic.databinding.FragmentListBinding


/**
 * @program: WanAndroid
 *
 * @description: 封装列表fragment
 *
 * @author: YangRT
 *
 * @create: 2020-02-19 11:06
 **/

abstract class BaseListFragment<D,M:BaseMvvmRepository<List<D>>,VM:BaseViewModel<D,M>>:BaseFragment<D,M,VM,FragmentListBinding>() {


    protected lateinit var adapter:BaseItemAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }



    override fun refreshCancel() {
        if (binding.mainPageRefreshLayout.isRefreshing){
            binding.mainPageRefreshLayout.isRefreshing = false
        }
    }

    override fun isRefreshing(): Boolean {
        return binding.mainPageRefreshLayout.isRefreshing
    }


    override fun loadMoreEmpty() {
        if(adapter.loadMoreModule.isLoading){
            adapter.loadMoreModule.loadMoreEnd()
        }
    }

    override fun loadMoreFailed() {
        if(adapter.loadMoreModule.isLoading){
            adapter.loadMoreModule.loadMoreFail()
        }
    }

    override fun loadMoreFinished() {
        if(adapter.loadMoreModule.isLoading){
            adapter.loadMoreModule.loadMoreComplete()
        }
    }


}