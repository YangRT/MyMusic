package com.example.mymusic.mine

import android.content.Intent
import com.example.mymusic.R
import com.example.mymusic.base.MutiBaseFragment
import com.example.mymusic.databinding.FragmentMineBinding

class MineFragment(): MutiBaseFragment<MineViewModel, FragmentMineBinding>() {

    override fun initView() {
        binding.playRecord.setOnClickListener {
            val intent = Intent(this.context, PlayRecordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun viewModel(): MineViewModel {
        return MineViewModel()
    }

    override fun refreshCancel() {

    }

    override fun isRefreshing(): Boolean {
        return false
    }

    override fun refresh() {

    }
}