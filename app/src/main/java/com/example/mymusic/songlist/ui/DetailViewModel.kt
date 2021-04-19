package com.example.mymusic.songlist.ui

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.MyApplication
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.base.model.Playlist
import com.example.mymusic.songlist.SongListApiImpl
import com.example.mymusic.songlist.repository.DetailRepository
import com.example.mymusic.utils.Constants
import com.example.mymusic.utils.getCookie

class DetailViewModel(val id: Long) : BaseViewModel<Playlist, DetailRepository>() {

    init {
        repository = DetailRepository(id)
    }

    val collectStatus = MutableLiveData<Boolean>()

    fun aboutCollect(type: Int) {
        val cookie = getCookie(Constants.DOMAIN)
        if (cookie != null && !TextUtils.isEmpty(cookie)) {
            launch({
                val time = System.currentTimeMillis()
                val result = SongListApiImpl.getCollectInfo(type, id, cookie, time)
                if (result.code == 301) {
                    Toast.makeText(MyApplication.context, "请先登录！", Toast.LENGTH_SHORT).show()
                } else if (result.code == 200) {
                    if (type == 1) {
                        collectStatus.postValue(true)
                    } else {
                        collectStatus.postValue(false)
                    }
                } else {
                    Toast.makeText(MyApplication.context, "网络错误！", Toast.LENGTH_SHORT).show()
                }
            }, {
                Toast.makeText(MyApplication.context, "网络错误！", Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(MyApplication.context, "请先登录", Toast.LENGTH_SHORT).show()
        }
    }

    class ViewModeFactory(private val id: Long) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailViewModel(id) as T
        }
    }
}