package com.example.mymusic.play

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.customview.progressbar.PlayingProcessBar
import com.example.mymusic.databinding.ActivityPlayMusicBinding
import com.example.mymusic.play.event.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class PlayMusicActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPlayMusicBinding
    private lateinit var viewModel: PlayMusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_play_music)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(PlayMusicViewModel::class.java)
        initView()
        EventBus.getDefault().register(this)
    }

    private fun initView() {
        binding.imageCurrent.setOnClickListener(this)
        binding.imageNext.setOnClickListener(this)
        binding.imagePrevious.setOnClickListener(this)

        binding.playProcessBar.setProcessChangeListener(object: PlayingProcessBar.ProcessChangeListener{
            override fun change(second: Int) {
                // 快进 快退
                val seekToEvent = SeekToEvent((second*1000).toLong())
                EventBus.getDefault().post(seekToEvent)
            }

        })
        viewModel.isPlaying.observe(this, Observer {
            if (it) {
               binding.imageCurrent.setImageResource(R.drawable.play_music)
            } else {
                binding.imageCurrent.setImageResource(R.drawable.pause_music)
            }
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id) {
                R.id.image_current -> {
                    if (viewModel.isPlaying.value == true) {
                        viewModel.isPlaying.postValue(false)
                        EventBus.getDefault().post(PauseEvent())
                    } else {
                        viewModel.isPlaying.postValue(true)
                        EventBus.getDefault().post(RestartEvent())
                    }
                }
                R.id.image_previous -> {
                    EventBus.getDefault().post(PreSongEvent())
                }
                R.id.image_next -> {
                    EventBus.getDefault().post(NextSongEvent())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun getPlayProcess(event: PlayingEvent) {
        val second = event.current / 1000
        binding.playProcessBar.changeProcess(second.toInt())
    }

    @Subscribe
    fun beginPlay(event: BeginPlayEvent) {
        val duration = (event.songInfo.duration / 1000).toInt()
        binding.playProcessBar.initProcessBar(duration)
        binding.playProcessBar.setCanModify(true)
    }

    @Subscribe
    fun pause(event: PauseFinishEvent) {

    }

    @Subscribe
    fun reStart(event: RestartFinishEvent) {

    }

    @Subscribe
    fun switchSong(event: BeginPlayEvent) {

    }


}