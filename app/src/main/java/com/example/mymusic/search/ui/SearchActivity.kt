package com.example.mymusic.search.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymusic.R
import com.example.mymusic.customview.search.BCallBack
import com.example.mymusic.customview.search.SCallBack
import com.example.mymusic.databinding.ActivitySearchBinding
import com.example.mymusic.search.identity.IdentityMusicActivity
import com.example.mymusic.search.model.HotWord
import com.example.mymusic.search.ui.adapter.HotWordAdapter
import com.example.mymusic.search.ui.adapter.SearchWordAdapter
import com.example.mymusic.search.ui.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private val hotWordList = ArrayList<HotWord>()
    private val historyList = ArrayList<String>()
    private var type: Int = 0
    private val requestPerCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        binding.lifecycleOwner = this
        // 改变状态栏颜色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(android.R.color.transparent)
        }
        setAndroidNativeLightStatusBar()

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.data.observe(this, Observer {
            (binding.searchHotWords.adapter as HotWordAdapter).setList(it)
        })
        initView()
        viewModel.getCacheData()
    }

    private fun initView() {
        val historyLayoutManager =  FlexboxLayoutManager(this)
        binding.searchHistory.layoutManager = historyLayoutManager
        binding.searchHistory.adapter =
            SearchWordAdapter(historyList)
        historyDataChange()

        val hotWordLayoutManager = GridLayoutManager(this,2)
        binding.searchHotWords.layoutManager = hotWordLayoutManager
        binding.searchHotWords.adapter =
            HotWordAdapter(hotWordList)
        binding.tvHistoryTitle.paint.isFakeBoldText = true
        binding.tvHotWordTitle.paint.isFakeBoldText = true
        binding.searchView.setBack(object : BCallBack{
            override fun backAction() {
                finish()
            }
        })
        binding.searchView.setSearchCallBack(object : SCallBack{
            override fun searchAction(text: String) {
                historyDataChange()
                // 搜索
                search(text)
            }
        })
        binding.imageDeleteHistory.setOnClickListener {
            val result = binding.searchView.deleteData()
            if (result){
                historyDataChange()
            }
        }
        (binding.searchHotWords.adapter as HotWordAdapter).setOnItemClickListener { adapter, view, position ->
            val word = (adapter.getItem(position) as HotWord).first
            search(word)
            if (viewModel.hasData(word)){
                viewModel.deleteItemData(word)
            }
            viewModel.insertData(word)
            historyDataChange()
        }
        (binding.searchHistory.adapter as SearchWordAdapter).setOnItemClickListener { adapter, view, position ->
            val historyWord = (adapter.getItem(position) as String)
            search(historyWord)
            if (viewModel.hasData(historyWord)){
                viewModel.deleteItemData(historyWord)
            }
            viewModel.insertData(historyWord)
            historyDataChange()
        }

        binding.identityMusicDefault.setOnClickListener {
            type = 0
            checkPermission()
        }

        binding.identityMusicAfs.setOnClickListener {
            type = 1
            checkPermission()
        }
    }

    private fun search(word: String) {
        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra("search_word", word)
        startActivity(intent)
        finish()
    }

    private fun historyDataChange() {
        val t = viewModel.queryData("")
        historyList.clear()
        historyList.addAll(t)
        binding.searchHistory.adapter?.notifyDataSetChanged()
    }


    // 改变状态栏字体颜色
    private fun setAndroidNativeLightStatusBar() {
        val decor = this.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，申请权限
            ActivityCompat.requestPermissions(this, permissions, requestPerCode);
        }else {
            //拥有权限
            val intent = Intent(this, IdentityMusicActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPerCode && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, IdentityMusicActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }else {
            Toast.makeText(this, "无法使用听歌识曲功能！", Toast.LENGTH_LONG).show()
        }
    }
}