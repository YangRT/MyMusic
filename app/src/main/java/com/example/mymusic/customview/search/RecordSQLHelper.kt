package com.example.mymusic.customview.search

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecordSQLHelper(context: Context) : SQLiteOpenHelper(context,"temp.db",null,1){

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("create table records(id integer primary key autoincrement,name varchar(200))")
    }

}