package com.example.mymusic.search.identity

import android.content.Intent
import android.graphics.PorterDuff
import android.media.MediaRecorder
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.customview.IdentityDialog
import com.example.mymusic.databinding.ActivityIdentityMusicBinding
import com.example.mymusic.search.ui.RecordStatus
import com.example.mymusic.search.ui.SearchResultActivity
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class IdentityMusicActivity : BaseActivity() {

    private lateinit var binding: ActivityIdentityMusicBinding
    private lateinit var viewModel: IdentityMusicViewModel

    private lateinit var voiceDictionaryPath: String

    private var mRecorder: MediaRecorder? = null

    private var type = 0

    /**
     * 录音生产的文件路径
     */
    private var voicePath: String? = null

    /**
     * 录音生产的文件名
     */
    private var voiceName: String? = null


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
        viewModel.result.observe(this, Observer {
            val result = JSONObject(it)
            if (!result.isNull("code") && result.getString("code") == "0") {
                val array = result.getJSONArray("data")
                if (array.length() > 0) {
                    val song = array.getJSONObject(0).getString("song")
                    val singer = array.getJSONObject(0).getString("singer")
                    IdentityDialog.Builder(this).setMessage("识别结果为$singer 的 $song").setSearchOnClickListener(
                        View.OnClickListener {
                            val intent = Intent(this, SearchResultActivity::class.java)
                            intent.putExtra("search_word", song)
                            startActivity(intent)
                            finish()
                        }).create().show()
                } else {
                    Toast.makeText(this, "找不到匹配歌曲", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "识别出错，请重试！", Toast.LENGTH_LONG).show()
            }
            viewModel.status.postValue(RecordStatus.IDENTITY_FINISH)
        })
        initTopBar()
        initView()

        voiceDictionaryPath = baseContext.filesDir.toString() + "/myRecord/"
    }

    private fun initTopBar() {
        setSupportActionBar(binding.toolbarIdentityMusic)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        type = intent.getIntExtra("type", 0)
        if (type == 0) {
            binding.toolbarTitle.text = "歌曲识别"
        } else {
            binding.toolbarTitle.text = "哼唱识别"
        }
    }

    private fun initView() {
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
                binding.lottieAnim.clearAnimation()
                binding.lottieAnim.setAnimation("listen_state.json")
            }
            RecordStatus.RECORD_FINISH -> {
                finishRecord()
                binding.lottieAnim.cancelAnimation()
                binding.lottieAnim.clearAnimation()
            }
            RecordStatus.RECORDING -> {
                beginRecord()
                binding.lottieAnim.playAnimation()
            }
            RecordStatus.IDENTITY -> {
                beginIdentity()
                binding.lottieAnim.setAnimation("loading.json")
                binding.lottieAnim.playAnimation()
            }
            RecordStatus.IDENTITY_FINISH -> {
                viewModel.status.postValue(RecordStatus.NO_BEGIN)
                binding.lottieAnim.clearAnimation()
            }
            else -> {}
        }
    }

    // 开始录音
    private fun beginRecord() {
        startRecord()
    }

    // 完成录音
    private fun finishRecord() {
        stopRecord()
    }

    // 开始识别
    private fun beginIdentity() {
        if (type == 0) {
            viewModel.identifyMusicDefault(voicePath!!)
        } else {
            viewModel.identifyMusicAfs(voicePath!!)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish() }
        }
        return true
    }


    private fun startRecord() {
        createVoiceNameAndPath()
        try {
            mRecorder = MediaRecorder()
            //设置声音来源
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            //设置输出格式
            mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            //设置输出路径
            mRecorder!!.setOutputFile(voicePath)
            //设置音频编码
            mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            //设置录制的音频通道数 通常是1(单声道)或2(立体声)
            mRecorder!!.setAudioChannels(2)
            //每秒采样的音频采样率。
            mRecorder!!.setAudioSamplingRate(8000)
            //音频编码比特率(以位/秒为单位)。
            mRecorder!!.setAudioEncodingBitRate(192000)

            mRecorder!!.maxAmplitude
            //准备
            mRecorder!!.prepare()
            //开始
            mRecorder!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createVoiceNameAndPath() {
        var count = 0
        val folder = File(voiceDictionaryPath)
        if (!folder.exists()) {
            //folder /SoundRecorder doesn't exist, create the folder
            folder.mkdir()
        }
        val time: String = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Date().time)
        voiceName = "record$time.mp3"
        voicePath = voiceDictionaryPath + voiceName!!
        val voiceFile: File = File(voicePath!!)
        if (voiceFile.exists()) {
            voiceFile.delete()
        }
    }


    private fun stopRecord() {
        mRecorder?.let {
            try {
                mRecorder!!.stop()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                mRecorder = null
                mRecorder = MediaRecorder()
            }
            mRecorder!!.release()
            mRecorder = null
        }
    }
}