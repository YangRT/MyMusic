package com.example.mymusic

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.View.SYSTEM_UI_FLAG_VISIBLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mymusic.databinding.ActivityMainBinding
import com.example.mymusic.find.ui.FindFragment
import com.example.mymusic.mine.MineFragment


class MainActivity : AppCompatActivity(),Observer<Int>, View.OnClickListener {

    private val TAG = "HomeActivity"

    private lateinit var binding:ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private val findFragment = FindFragment()
    private val mineFragment = MineFragment()
    private lateinit var from :Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        // 改变状态栏颜色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(android.R.color.transparent)
        }
        setAndroidNativeLightStatusBar()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        initView()
    }

    override fun onChanged(t: Int?) {

    }

    //
    override fun onClick(v: View?) {
        var fragment:Fragment? = null
        when(v?.id){
            R.id.main_menu -> {

            }
            R.id.main_music -> {

            }
            R.id.main_tv_find -> {
                fragment = findFragment
                viewModel.selectedFirst.postValue(true)

            }
            R.id.main_tv_me -> {
                fragment = mineFragment
                viewModel.selectedFirst.postValue(false)

            }
        }
        fragment?.let { i ->
            if(i == from){
                return
            }
            switchFragment(from, i)
            from = i
        }
    }

    private fun initView(){
        binding.mainTvFind.setOnClickListener(this)
        binding.mainTvMe.setOnClickListener(this)
        binding.mainMenu.setOnClickListener(this)
        binding.mainMusic.setOnClickListener(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_fragment_container, findFragment).commit()
        from = findFragment
    }

    private fun switchFragment(from: Fragment, to:Fragment){
        if(from != to){
            val transaction = supportFragmentManager.beginTransaction()
            if(!to.isAdded){
                transaction.hide(from).add(R.id.main_fragment_container,to).commit()
            }else{
                transaction.hide(from)
                transaction.show(to).commit()
            }
        }
    }

    // 改变状态栏字体颜色
    private fun setAndroidNativeLightStatusBar() {
        val decor = this.window.decorView
        decor.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}