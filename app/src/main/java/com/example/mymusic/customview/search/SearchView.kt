package com.example.mymusic.customview.search

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide.init
import com.example.mymusic.R
import com.example.mymusic.search.model.HotWord
import kotlinx.android.synthetic.main.search_view.view.*


class SearchView@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int = 0):
    LinearLayout(context,attributeSet,defStyleAttr) {

    private lateinit var baseAdapter: BaseAdapter
    private lateinit var hotWordAdapter: HotWordAdapter
    private val hotWordList = ArrayList<HotWord>()

    private var recordSQLHelper: RecordSQLHelper = RecordSQLHelper(context)
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

        edit_search.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                // 每次输入后，模糊查询数据库 & 显示
                queryData(edit_search.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
//        history_listview.setOnItemClickListener { parent, view, position, id ->
//            val textView =  view.findViewById<TextView>(android.R.id.text1)
//            val name = textView.text.toString()
//            edit_search.setText(name)
//            Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
//        }

        image_search.setOnClickListener {
            if(edit_search.text.toString() == ""){
                Toast.makeText(context, "搜索不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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
        }
    }

    private fun initView(){
        LayoutInflater.from(context).inflate(R.layout.search_view,this)
        search_block.setBackgroundColor(Color.parseColor("#03A9F4"))
        edit_search.setHintTextColor(Color.WHITE)
        edit_search.setTextColor(Color.WHITE)
        edit_search.textSize = 16f
        edit_search.hint = "点击搜索"
        hotWordAdapter = HotWordAdapter(hotWordList)
        val manager = GridLayoutManager(context, 2)
        search_hot_word_recycler_view.layoutManager = manager
        search_hot_word_recycler_view.adapter = hotWordAdapter
    }

    private fun queryData(str:String){
        val cursor = recordSQLHelper.readableDatabase.rawQuery("select id as _id,name from records where name like '%$str%' order by id desc ", null)

        baseAdapter = SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, cursor,
            arrayOf("name"), intArrayOf(android.R.id.text1 ), CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
//        history_listview.adapter = baseAdapter
//        baseAdapter.notifyDataSetChanged()
//        if (str == "" && cursor.count != 0){
//            tv_clear.visibility = VISIBLE
//        }
//        else {
//            tv_clear.visibility = INVISIBLE
//        }
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

    private fun insertData(str:String){
        db = recordSQLHelper.writableDatabase
        db.execSQL("insert into records(name) values('$str')")
        db.close()
    }

    fun updateHotWordData(data: ObservableArrayList<HotWord>){
        hotWordAdapter.updateData(data)
    }


}