package com.example.mymusic.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alguojian.mylibrary.StatusLayout

abstract class MutiBaseFragment<VM:ViewModel,T: ViewDataBinding>: Fragment(), Observer<Any> {

    protected lateinit var binding:T
    protected lateinit var  statusHelper: StatusLayout.StatusHelper


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        retainInstance = true
        val statusLayout = StatusLayout.setNewAdapter(BaseStatusAdapter())
        statusHelper = statusLayout.attachView(binding.root)
            .onRetryClick {
                refresh()
            }
        initView()
        return StatusLayout.getRootView()
    }


    override fun onChanged(t: Any?) {
        if(t is PageStatus){
            Log.e("BaseFragment", "change$t")
            when(t){
                PageStatus.LOADING -> {
                    // statusHelper.showLoading()
                }
                PageStatus.SHOW_CONTENT -> {
                    statusHelper.showSuccess()
                    if(isRefreshing()){
                        Toast.makeText(context,"刷新成功！", Toast.LENGTH_SHORT).show();
                        refreshCancel()
                    }
                }
                PageStatus.EMPTY -> {
                    Log.e("BaseFragment","Empty")
                    statusHelper.showEmpty()
                }
                PageStatus.NO_MORE_DATA -> { }
                PageStatus.LOAD_MORE_FAILED -> { }
                PageStatus.REFRESH_ERROR -> Toast.makeText(context,"刷新失败！", Toast.LENGTH_SHORT).show()
                PageStatus.REQUEST_ERROR -> Toast.makeText(context,"请求失败,请检查网络！", Toast.LENGTH_SHORT).show()
                PageStatus.NETWORK_ERROR -> {
                    // statusHelper.showFailed()
                }
            }
        }
    }

    abstract fun initView()

    abstract fun getLayoutId():Int

    abstract fun viewModel():VM

    abstract fun refreshCancel()

    abstract fun refresh()

    abstract fun isRefreshing():Boolean

    override fun onDestroy() {
        super.onDestroy()
        StatusLayout.clearNewAdapter()
    }
}