package com.example.wanandroid.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer


/**
 * @program: WanAndroid
 *
 * @description: 实现懒加载
 *
 * @author: YangRT
 *
 * @create: 2020-02-21 21:42
 **/

abstract class BaseLazyFragment<D,M:BaseMvvmRepository<List<D>>,VM:BaseViewModel<D,M>>:BaseListFragment<D,M,VM>(){

    private var isFirstVisible:Boolean = true
    private var viewIsCreated :Boolean = false
    private var currentVisibleState:Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycle.addObserver(viewModel())
        viewModel().status.observe(this,this)
        viewModel().data.observe(this,
            Observer<ObservableArrayList<D>> { dataInsert(it)})
        viewIsCreated = true
    }

    override fun onResume() {
        super.onResume()
        if (!isHidden && !currentVisibleState && isResumed) {
            dispatchVisibleState(true)
        }
    }

    override fun onPause() {
        super.onPause()
        if (currentVisibleState && !isResumed) {
            dispatchVisibleState(false)
        }
    }

    protected open fun onFragmentFirstVisible() {
        Log.d("lazy", "第一次可见,进行当前Fragment初始化操作")
        init()
    }

    protected open fun onFragmentResume() {
        Log.d("lazy", "onFragmentResume 执行网络请求以及，UI操作")
    }

    protected open fun onFragmentPause() {
        Log.d(fragmentTag(), "onFragmentPause 中断网络请求，UI操作")
    }

    private fun dispatchVisibleState(isVisible:Boolean) {
        //为了兼容内嵌ViewPager的情况,分发时，还要判断父Fragment是不是可见
        if (isVisible == currentVisibleState) return//如果目标值，和当前值相同，那就别费劲了
        currentVisibleState = isVisible//更新状态值
        if (isVisible) {//如果可见
            //那就区分是第一次可见，还是非第一次可见
            if (isFirstVisible) {
                isFirstVisible = false
                onFragmentFirstVisible()
            }
            onFragmentResume()
        } else {
            onFragmentPause()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden){
            dispatchVisibleState(false)
        }else{
            dispatchVisibleState(true)
        }
    }


}