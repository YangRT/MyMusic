package com.example.mymusic.search.ui.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.search.ui.RecordStatus
import kotlinx.coroutines.launch

class IdentityMusicViewModel: ViewModel(), LifecycleObserver {



    var status = MutableLiveData<RecordStatus>()

    // 听歌识曲
    fun identifyMusicDefault(path: String) {

    }

    // 哼唱识曲
    fun identifyMusicAfs(path: String) {

    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}