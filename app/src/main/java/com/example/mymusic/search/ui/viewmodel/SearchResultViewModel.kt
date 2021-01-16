package com.example.mymusic.search.ui.viewmodel

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymusic.MyApplication
import com.example.mymusic.search.ui.RecordSQLHelper

class SearchResultViewModel: ViewModel() {

    private var recordSQLHelper: RecordSQLHelper =
        RecordSQLHelper(MyApplication.context)
    private lateinit var db: SQLiteDatabase

    var searchWord = MutableLiveData<String>()

    var currentIndex = MutableLiveData<Int>()

    fun insertData(str:String){
        db = recordSQLHelper.writableDatabase
        db.execSQL("insert into records(name) values('$str')")
        db.close()
    }


}