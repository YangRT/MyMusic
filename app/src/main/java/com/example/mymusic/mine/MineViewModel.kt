package com.example.mymusic.mine

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.MyApplication
import com.example.mymusic.network.BaseUrl
import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.utils.Constants
import com.example.mymusic.utils.getCookie
import com.example.mymusic.utils.saveData
import kotlinx.coroutines.launch

class MineViewModel : ViewModel(){

    private val mineService = ServiceCreator.create(MineService::class.java)

    val loginStatus = MutableLiveData<Boolean>()
    val accountInformation = MutableLiveData<StatusData>()

    fun getLoginStatus() {
        launch(
            {
                val cookie = getCookie(Constants.DOMAIN)
                cookie?.let {
                    val result = mineService.getStatus(cookie).await()
                    Log.e("MineFragment", "data: ${result.data}")
                    if (result.data.account != null) {
                        loginStatus.postValue(true)
                        accountInformation.postValue(result.data)
                        Toast.makeText(MyApplication.context,"", Toast.LENGTH_SHORT).show()
                    } else {
                        loginStatus.postValue(false)
                        Toast.makeText(MyApplication.context,"", Toast.LENGTH_SHORT).show()
                    }
                }
                if (cookie == null) {
                    Log.e("MineFragment", "cookie null")
                    loginStatus.postValue(false)
                }
            },{
            Log.e("MineFragment", "error ${it.message}")
            Toast.makeText(MyApplication.context,"网络错误！", Toast.LENGTH_SHORT).show()
            }
        )
    }


    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}