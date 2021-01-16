package com.example.mymusic.customview.search

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.*
import com.example.mymusic.R
import com.example.mymusic.search.ui.adapter.HotWordAdapter
import com.example.mymusic.search.ui.RecordSQLHelper
import kotlinx.android.synthetic.main.search_view.view.*
import java.sql.SQLException


class SearchView@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int = 0):
    LinearLayout(context,attributeSet,defStyleAttr) {

    private lateinit var hotWordAdapter: HotWordAdapter

    private var recordSQLHelper: RecordSQLHelper =
        RecordSQLHelper(context)
    private lateinit var db: SQLiteDatabase

    private var bCallBack:BCallBack? = null
    private var sCallBack:SCallBack? = null

    init {
        init()
    }

    private fun init(){
        initView()
        queryData("")

        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        edit_search.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                if(edit_search.text.toString() == ""){
                    Toast.makeText(context, "搜索不能为空", Toast.LENGTH_SHORT).show()
                    return@setOnKeyListener true
                }

                sCallBack?.searchAction(edit_search.text.toString())
                Toast.makeText(context, "需要搜索的是" + edit_search.text, Toast.LENGTH_SHORT).show()

                // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                val hasData = hasData(edit_search.text.toString().trim())
                // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                if (!hasData) {
                    insertData(edit_search.text.toString().trim())
                    queryData("")
                }
                edit_search.setText("")
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        edit_search.setBackListener { bCallBack?.backAction() }



        image_search.setOnClickListener {
            if(edit_search.text.toString() == ""){
                Toast.makeText(context, "搜索不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(context, "需要搜索的是" + edit_search.text, Toast.LENGTH_SHORT).show()

            // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
            val hasData = hasData(edit_search.text.toString().trim())
            // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
            if (!hasData) {
                insertData(edit_search.text.toString().trim())
            }else {
                deleteItemData(edit_search.text.toString().trim())
                insertData(edit_search.text.toString().trim())
            }
            sCallBack?.searchAction(edit_search.text.toString())
            edit_search.setText("")
        }
    }

    private fun initView(){
        LayoutInflater.from(context).inflate(R.layout.search_view,this)
        edit_search.setTextColor(resources.getColor(R.color.black))
        edit_search.textSize = 16f
        edit_search.hint = "搜索"
    }

    private fun queryData(str:String){
        val cursor = recordSQLHelper.readableDatabase.rawQuery("select id as _id,name from records where name like '%$str%' order by id desc ", null)
    }

    fun deleteData(): Boolean{
        db = recordSQLHelper.writableDatabase
        try {
            db.execSQL("delete from records")
        }catch (e : SQLException){
            return false
        }finally {
            db.close()
        }
        return true
    }

    private fun hasData(str:String):Boolean{
        val cursor = recordSQLHelper.readableDatabase.rawQuery(
            "select id as _id,name from records where name =?", arrayOf(str))
        //  判断是否有下一个
        val result = cursor.moveToNext()
        cursor.close()
        return result
    }

    private fun insertData(str:String): Boolean{
        db = recordSQLHelper.writableDatabase
        try {
            db.execSQL("insert into records(name) values('$str')")
        }catch (e : SQLException){
            return false
        }finally {
            db.close()
        }
        return true
    }

    private fun deleteItemData(str: String) {
        db = recordSQLHelper.writableDatabase
        db.execSQL("delete from records where name='${str}'")
    }

    fun setBack(back: BCallBack){
        this.bCallBack = back
    }

    fun setSearchCallBack(callback: SCallBack) {
        this.sCallBack = callback
    }

    fun setSearchString(word: String) {
        edit_search.setText(word)
    }

}