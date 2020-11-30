package com.example.mymusic.base

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.MyApplication
import kotlinx.coroutines.launch

/**
 * @program: WanAndroid
 *
 * @description: mvvm架构vm层基类
 *
 * @author: YangRT
 *
 * @create: 2020-02-15 11:32
 **/

open class BaseViewModel<D,M: BaseMvvmRepository<List<D>>>:ViewModel(), LifecycleObserver {

    var data = MutableLiveData<ObservableArrayList<D>>()

    var refresh = MutableLiveData<Boolean>()

    lateinit var repository:M

    var status = MutableLiveData<PageStatus>()

    var isRequesting = false

    var isFirst = true

    init {
        data.value = ObservableArrayList<D>()
        status.value = PageStatus.LOADING
    }


    fun getCacheData(){
        launch({
            var result = repository.getCacheData()
            Log.e("getCacheData","result:"+result.data.toString())
            dealWithResult(result)
            if (repository.isNeedToUpdate()){
                requestData()
            }
        },{
            it.message?.let { it1 -> Log.e("BaseViewModel", it1) }
        })
    }

    private fun requestData(){
        launch({
            Log.e("launch",Thread.currentThread().name)
            var result = repository.requestData()
            isFirst = false
            dealWithResult(result)
        },{
            Log.e("BaseViewModel",it.message+"requestData")
            isFirst = false
            if (data.value?.size == 0){
                status.postValue(PageStatus.NETWORK_ERROR)
            }
            Toast.makeText(MyApplication.context,"网络错误！",Toast.LENGTH_SHORT).show()
        })
    }

    fun refresh(){
        if (status.value != PageStatus.LOADING){
            Log.e("BaseViewModel","refresh")
            refresh.value = true
            launch({
                var result = repository.refresh()
                refresh.value = false
                dealWithResult(result)
            },{
                refresh.value = false
                status.postValue(PageStatus.REFRESH_ERROR)
            })
        }

    }

    protected fun dealWithResult(result: BaseResult<List<D>>){
        Log.e("BaseViewModel","result："+result.data.toString()+" "+result.isFirst)
        if(result.isEmpty){
            if (!result.isFromCache && result.msg != null){
                Toast.makeText(MyApplication.context,result.msg,Toast.LENGTH_SHORT).show()
            }
            if(!result.isFromCache && data.value?.size == 0){
               status.postValue(PageStatus.EMPTY)
           }else if (!result.isFromCache && result.isFirst){
               status.postValue(PageStatus.EMPTY)
           }else if(!result.isFromCache && result.isPaging){
               status.postValue(PageStatus.NO_MORE_DATA)
           }
       } else{
               if(result.isFirst){
                   Log.e("BaseViewModel","clear")
                   data.value?.clear()
               }
               result.data?.let { data.value?.addAll(it) }
               data.postValue(data.value)
               status.postValue(PageStatus.SHOW_CONTENT)

       }
    }

    protected fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

    }


