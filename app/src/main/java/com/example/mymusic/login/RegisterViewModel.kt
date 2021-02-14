package com.example.mymusic.login

import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.MyApplication
import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel(), LifecycleObserver {

    private val registerService = ServiceCreator.create(RegisterService::class.java)

    val registerStatus = MutableLiveData<Boolean>()


    fun login(phone: String, password: String, captcha: String, nickname: String) {
        launch(
            {
                val result = registerService.register(phone, password, captcha, nickname).await()
                if (result.code == 200) {
                    registerStatus.postValue(true)
                    Toast.makeText(MyApplication.context,"注册成功！", Toast.LENGTH_SHORT).show()
                } else {
                    registerStatus.postValue(false)
                    Toast.makeText(MyApplication.context,result.msg, Toast.LENGTH_SHORT).show()

                }
            },{
                Toast.makeText(MyApplication.context,"网络错误！", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun getCaptcha(phone: String) {
        launch({
            val result = registerService.getCaptcha(phone).await()
            Toast.makeText(MyApplication.context,result.message, Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(MyApplication.context,"网络错误！", Toast.LENGTH_SHORT).show()
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