package com.example.mymusic.mine

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.base.MutiBaseFragment
import com.example.mymusic.databinding.FragmentMineBinding
import com.example.mymusic.login.LoginActivity
import com.example.mymusic.login.LoginViewModel
import com.example.mymusic.utils.getSaveData

class MineFragment() : MutiBaseFragment<MineViewModel, FragmentMineBinding>() {


    override fun initView() {
        viewModel().loginStatus.observe(this, Observer {
            binding.isLogin = it
            Log.e("MineFragment","isLogin: $it")
            binding.executePendingBindings()
        })
        viewModel().getLoginStatus()
        binding.localMusic.setOnClickListener {
            val intent = Intent(this.context, MineSongActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }
        binding.playRecord.setOnClickListener {
            val intent = Intent(this.context, MineSongActivity::class.java)
            intent.putExtra("type", 0)
            startActivity(intent)
        }
        binding.downloadMusic.setOnClickListener {
            val intent = Intent(this.context, MineSongActivity::class.java)
            intent.putExtra("type", 2)
            startActivity(intent)
        }
        binding.loginTip.setOnClickListener {
            val intent = Intent(this.context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun viewModel(): MineViewModel {
        if (viewModel == null) {
            viewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        }
        return viewModel as MineViewModel
    }

    override fun refreshCancel() {

    }

    override fun isRefreshing(): Boolean {
        return false
    }

    override fun refresh() {

    }
}