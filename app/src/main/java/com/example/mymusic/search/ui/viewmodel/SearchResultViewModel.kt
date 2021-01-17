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
        val has = hasData(str)
        if (has) return
        db = recordSQLHelper.writableDatabase
        db.execSQL("insert into records(name) values('$str')")
        db.close()
    }

    private fun hasData(str:String):Boolean{
        val cursor = recordSQLHelper.readableDatabase.rawQuery(
            "select id as _id,name from records where name =?", arrayOf(str))
        //  判断是否有下一个
        val result = cursor.moveToNext()
        cursor.close()
        return result
    }

}