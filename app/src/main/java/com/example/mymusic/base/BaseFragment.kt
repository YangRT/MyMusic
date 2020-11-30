package com.example.mymusic.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alguojian.mylibrary.StatusLayout

/**
 * @program: WanAndroid
 *
 * @description: mvvm架构v层基类
 *
 * @author: YangRT
 *
 * @create: 2020-02-15 22:50
 **/

abstract class BaseFragment<D,M: BaseMvvmRepository<List<D>>,VM: BaseViewModel<D, M>,T: ViewDataBinding>:Fragment(),
    Observer<Any> {

    protected var viewModel:VM? = null
    protected lateinit var binding:T
    protected lateinit var  statusHelper: StatusLayout.StatusHelper

    abstract fun getLayoutId():Int
    abstract fun viewModel():VM
    abstract fun dataInsert(data:ObservableArrayList<D>)
    abstract fun refreshCancel()
    abstract fun isRefreshing():Boolean
    abstract fun fragmentTag():String
    abstract fun init()

    open fun loadMoreFailed(){}
    open fun loadMoreFinished(){}
    open fun loadMoreEmpty(){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        retainInstance = true
        val statusLayout = StatusLayout.setNewAdapter(BaseStatusAdapter())
        statusHelper = statusLayout.attachView(binding.root)
                    .onRetryClick {
                        viewModel().refresh()
                    }
        return StatusLayout.getRootView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel())
        viewModel().status.observe(viewLifecycleOwner,this)
        viewModel().data.observe(viewLifecycleOwner,
            Observer<ObservableArrayList<D>> { dataInsert(it)})
        init()
    }

    override fun onChanged(t: Any) {
        if(t is PageStatus){
            Log.e("BaseFragment", "change$t")
            when(t){
                PageStatus.LOADING -> {
                    statusHelper.showLoading()
                    }
                PageStatus.SHOW_CONTENT -> {
                    statusHelper.showSuccess()
                    if(isRefreshing()){
                        Toast.makeText(context,"刷新成功！",Toast.LENGTH_SHORT).show();
                        refreshCancel()
                    }
                    loadMoreFinished()
                }
                PageStatus.EMPTY -> {
                    Log.e("BaseFragment","Empty")
                    statusHelper.showEmpty()
                }
                PageStatus.NO_MORE_DATA -> {loadMoreEmpty()}
                PageStatus.LOAD_MORE_FAILED -> loadMoreFailed()
                PageStatus.REFRESH_ERROR -> Toast.makeText(context,"刷新失败！",Toast.LENGTH_SHORT).show()
                PageStatus.REQUEST_ERROR -> Toast.makeText(context,"请求失败,请检查网络！",Toast.LENGTH_SHORT).show();
                PageStatus.NETWORK_ERROR -> {
                    statusHelper.showFailed()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusLayout.clearNewAdapter()
    }

}