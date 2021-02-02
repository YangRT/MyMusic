package com.example.mymusic.search.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityIdentityMusicBinding
import com.example.mymusic.search.record.RecordService
import com.example.mymusic.search.ui.viewmodel.IdentityMusicViewModel

class IdentityMusicActivity : BaseActivity() {

    private lateinit var binding: ActivityIdentityMusicBinding
    private lateinit var viewModel: IdentityMusicViewModel
    private var mRecordPromptCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_identity_music)
        viewModel = ViewModelProvider(this).get(IdentityMusicViewModel::class.java)
        viewModel.status.observe(this, Observer {
            when(it) {
                RecordStatus.NO_BEGIN -> { binding.btnControl.setText(R.string.begin_record) }
                RecordStatus.RECORD_FINISH -> { binding.btnControl.setText(R.string.begin_identify) }
                RecordStatus.RECORDING -> { binding.btnControl.setText(R.string.finish_record)}
                RecordStatus.IDENTITY -> { binding.btnControl.setText(R.string.identify) }
                else -> {}
            }
            statusChange(it)
        })
        initTopBar()
        initView()
    }

    private fun initTopBar() {
        setSupportActionBar(binding.toolbarIdentityMusic)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbarTitle.text = "歌曲识别"
    }

    private fun initView() {
//        val type = intent.getIntExtra("type", 0)
//        if (type == 0) {
//            binding.lottieAnim.setAnimation("listen_state.json")
//        } else {
//            binding.lottieAnim.setAnimation("voice.json")
//        }
        binding.lottieAnim.setAnimation("listen_state.json")
        viewModel.status.postValue(RecordStatus.NO_BEGIN)

        binding.btnControl.setOnClickListener {
            if (viewModel.status.value == RecordStatus.NO_BEGIN) {
                viewModel.status.postValue(RecordStatus.RECORDING)
            } else if (viewModel.status.value == RecordStatus.RECORD_FINISH) {
                viewModel.status.postValue(RecordStatus.IDENTITY)
            } else if (viewModel.status.value == RecordStatus.RECORDING) {
                viewModel.status.postValue(RecordStatus.RECORD_FINISH)
            }
        }
    }

    private fun statusChange(status: RecordStatus) {
        when(status) {
            RecordStatus.NO_BEGIN -> {
                binding.lottieAnim.pauseAnimation()
            }
            RecordStatus.RECORD_FINISH -> {
                finishRecord()
                binding.lottieAnim.cancelAnimation()
            }
            RecordStatus.RECORDING -> {
                beginRecord()
                binding.lottieAnim.playAnimation()
            }
            RecordStatus.IDENTITY -> {
                binding.lottieAnim.setAnimation("loading.json")
                binding.lottieAnim.playAnimation()
            }
            RecordStatus.IDENTITY_FINISH -> {
                binding.lottieAnim.cancelAnimation()
            }
            else -> {}
        }
    }

    // 开始录音
    private fun beginRecord() {
        startOrStopRecordVoice(true)
    }

    // 完成录音
    private fun finishRecord() {
        startOrStopRecordVoice(false)
    }

    // 开始识别
    private fun beginIdentity() {

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish() }
        }
        return true
    }

    private fun startOrStopRecordVoice(isStartRecord: Boolean) {
        val intent = Intent(this, RecordService::class.java)
        if (isStartRecord) {
            this.startService(intent)
            mRecordPromptCount++
        } else {
            this.stopService(intent)
        }

    }
}