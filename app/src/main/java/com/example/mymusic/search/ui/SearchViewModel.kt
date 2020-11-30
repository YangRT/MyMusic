package com.example.mymusic.search.ui

import android.database.sqlite.SQLiteDatabase
import com.example.mymusic.MyApplication.Companion.context
import com.example.mymusic.search.model.HotWord
import com.example.mymusic.search.repository.HotWordsRepository
import com.example.mymusic.base.BaseViewModel


/**
 * @program: MyMusic
 *
 * @description: 搜索 viewmodel
 *
 * @author: YangRT
 *
 * @create: 2020-11-25 22:57
 **/

class SearchViewModel: BaseViewModel<HotWord, HotWordsRepository>() {

    private var recordSQLHelper: RecordSQLHelper =
        RecordSQLHelper(context)
    private lateinit var db: SQLiteDatabase

    init {
        repository = HotWordsRepository()
    }

    fun queryData(str:String): List<String> {
        val list = ArrayList<String>()
        val cursor = recordSQLHelper.readableDatabase.rawQuery("select id as _id,name from records where name like '%$str%' order by id desc ", null)
        if (cursor.moveToLast()) {
            do {
                val str = cursor.getString(cursor.getColumnIndex("name"))
                list.add(str)
            }while (cursor.moveToPrevious())
        }
        return list
    }

    private fun deleteData(){
        db = recordSQLHelper.writableDatabase
        db.execSQL("delete from records")
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

    fun insertData(str:String){
        db = recordSQLHelper.writableDatabase
        db.execSQL("insert into records(name) values('$str')")
        db.close()
    }

}