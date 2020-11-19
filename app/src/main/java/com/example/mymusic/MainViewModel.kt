package com.example.mymusic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val selectedFirst = MutableLiveData<Boolean>(true)

}