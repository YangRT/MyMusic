package com.example.mymusic.search.identity

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.search.ui.RecordStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.HashMap

class IdentityMusicViewModel: ViewModel(), LifecycleObserver {

    // webapi接口地址
    private val WEBSING_URL = "https://webqbh.xfyun.cn/v1/service/v1/qbh"

    // 应用ID
    private val APPID = "6006aa1f"

    // 接口密钥
    private val API_KEY = "8d6f6fc571289d72bf7b12f9223d177a"

    private var ENGINE_TYPE = "afs"


    private val AUE = "aac"


    var status = MutableLiveData<RecordStatus>()

    var result = MutableLiveData<String>()

    // 听歌识曲
    fun identifyMusicDefault(path: String) {
        val param = "{\"aue\":\"$AUE\",\"sample_rate\":\"8000\"}"
        val header = buildHttpHeader(param)
        val audioByteArray = FileUtil.read(path)
        launch({
            val res = withContext(Dispatchers.IO) {
                 HttpUtil.doPost(WEBSING_URL, header, audioByteArray)
            }
            result.postValue(res)
        },{

        })
    }

    // 哼唱识曲
    fun identifyMusicAfs(path: String) {
        ENGINE_TYPE = "afs"
        val param = "{\"aue\":\"$AUE\",\"engine_type\":\"$ENGINE_TYPE\",\"sample_rate\":\"8000\"}"
        val header = buildHttpHeader(param)
        val audioByteArray = FileUtil.read(path)
        launch({
            val res = withContext(Dispatchers.IO) {
                HttpUtil.doPost(WEBSING_URL, header, audioByteArray)
            }
            result.postValue(res)
        },{

        })
    }

    private fun buildHttpHeader(param: String): Map<String, String>? {
        val curTime: String = (System.currentTimeMillis() / 1000L).toString()
        // 如果使用audio_url传输音频，须在param中添加audio_url参数
        var paramBase64 = FileUtil.getBase64(param)
        val checkSum: String = FileUtil.digest(API_KEY + curTime + paramBase64)
        val header: MutableMap<String, String> =
            HashMap()
        header["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8"
        header["X-Param"] = paramBase64
        header["X-CurTime"] = curTime
        header["X-CheckSum"] = checkSum
        header["X-Appid"] = APPID
        return header
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

}