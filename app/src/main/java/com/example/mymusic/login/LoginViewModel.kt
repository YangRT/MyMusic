package com.example.mymusic.login

import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.MyApplication
import com.example.mymusic.network.ServiceCreator
import com.example.mymusic.network.await
import com.example.mymusic.utils.Constants
import com.example.mymusic.utils.getCookie
import com.example.mymusic.utils.saveCookies
import com.example.mymusic.utils.saveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * @program: MyMusic
 *
 * @description: 登录viewmodel
 *
 * @author: YangRT
 *
 * @create: 2020-11-20 00:54
 **/

class LoginViewModel: ViewModel(), LifecycleObserver {

    private val loginService = ServiceCreator.createLogin(LoginService::class.java)

    val loginStatus = MutableLiveData<Boolean>()

    fun login(phone: String, password: String) {
        launch(
            {
                val result = loginService.login(phone, password).await()
                if (result.code == 200) {
                    val cookie = getCookie(Constants.TEMP_DOMAIN)
                    cookie?.let {
                        saveCookies(Constants.DOMAIN, cookie)
                        saveData("user", phone)
                        loginStatus.postValue(true)
                        Toast.makeText(MyApplication.context,"登录成功！", Toast.LENGTH_SHORT).show()
                    }
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