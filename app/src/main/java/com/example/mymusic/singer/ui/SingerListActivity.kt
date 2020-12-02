package com.example.mymusic.singer.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.mymusic.R
import com.example.mymusic.databinding.ActivitySingerListBinding

class SingerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singer_list)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_singer_list)
        binding.lifecycleOwner = this

        // 改变状态栏颜色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(android.R.color.transparent)
        }
        setAndroidNativeLightStatusBar()
    }

    // 改变状态栏字体颜色
    private fun setAndroidNativeLightStatusBar() {
        val decor = this.window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}