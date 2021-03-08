package com.example.mymusic.mine

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.MyApplication
import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.utils.saveData
import kotlinx.coroutines.launch

class MineViewModel : ViewModel(){

    private val mineService = ServiceCreator.createLogin(MineService::class.java)

    val loginStatus = MutableLiveData<Boolean>()

    fun getAccount() {
        launch(
            {
                val result = mineService.getAccount().await()
                if (result.code == 200 && result.account != null) {
                    loginStatus.postValue(true)
                    Toast.makeText(MyApplication.context,result.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    loginStatus.postValue(false)
                    Toast.makeText(MyApplication.context,"未登录", Toast.LENGTH_SHORT).show()
                }
            },{
                Toast.makeText(MyApplication.context,"网络错误！", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun getLoginStatus() {
        launch(
            {
                val result = mineService.getStatus().await()
                if (result.code == 200 ) {
                    loginStatus.postValue(true)
                    Toast.makeText(MyApplication.context,result.msg, Toast.LENGTH_SHORT).show()
                } else {
                    loginStatus.postValue(false)
                    Toast.makeText(MyApplication.context,result.msg, Toast.LENGTH_SHORT).show()
                }
            },{
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