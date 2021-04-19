package com.example.mymusic.mine.viewmodel

import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.MyApplication
import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.songlist.api.GetCollectService
import com.example.mymusic.songlist.model.CategoryList
import com.example.mymusic.utils.Constants
import com.example.mymusic.utils.getCookie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectPlayListViewModel: ViewModel(), LifecycleObserver {

    private val getCollectService = ServiceCreator.create(GetCollectService::class.java)

    val data = MutableLiveData<ObservableArrayList<CategoryList>>()

    init {
        data.value = ObservableArrayList()
    }

    fun getData(uid: String) {
        launch({
            val cookie = getCookie(Constants.DOMAIN)
            if (cookie != null && !TextUtils.isEmpty(cookie)) {
                val time = System.currentTimeMillis()
                val result = withContext(Dispatchers.IO) { getCollectService.getCollectPlayList(cookie, uid, time).await() }
                if (result.code == 301) {
                    data.value?.clear()
                    data.postValue(data.value)
                    Toast.makeText(MyApplication.context, "请先登录！", Toast.LENGTH_SHORT).show()
                } else if (result.code == 200) {
                        data.value?.clear()
                        data.value?.addAll(result.playlist)
                        data.postValue(data.value)
                } else {
                    Toast.makeText(MyApplication.context, "网络错误！", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(MyApplication.context, "请先登录！", Toast.LENGTH_SHORT).show()
            }
        }, {
            Toast.makeText(MyApplication.context, "网络错误！", Toast.LENGTH_SHORT).show()
        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}