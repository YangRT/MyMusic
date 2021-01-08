package com.example.mymusic.radio.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseViewModel
import com.example.mymusic.radio.model.Program
import com.example.mymusic.radio.repository.ProgramDetailRepository


/**
 * @program: MyMusic
 *
 * @description: 节目详情viewmodel
 *
 * @author: YangRT
 *
 * @create: 2021-01-08 22:22
 **/

class ProgramDetailViewModel(val id: Long): BaseViewModel<Program, ProgramDetailRepository>() {

    init {
        repository = ProgramDetailRepository(id)
    }

    class ViewModeFactory(private val id: Long) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProgramDetailViewModel(id) as T
        }
    }
}