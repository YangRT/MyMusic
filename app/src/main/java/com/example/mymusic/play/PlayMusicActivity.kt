package com.example.mymusic.play

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.customview.progressbar.PlayingProcessBar
import com.example.mymusic.databinding.ActivityPlayMusicBinding
import com.example.mymusic.play.event.*
import com.example.mymusic.utils.BlurUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PlayMusicActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPlayMusicBinding
    private lateinit var viewModel: PlayMusicViewModel
    private var animator : ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        binding = DataBindingUtil.setContentView(this,R.layout.activity_play_music)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(PlayMusicViewModel::class.java)
        initView()
        EventBus.getDefault().register(this)
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarPlayMusic)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


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
               binding.imageCurrent.setImageResource(R.drawable.play_icon)
                animator?.resume()
                val needleAnimator = ObjectAnimator.ofFloat(binding.imageNeedle, "rotation", -15f, 0f)
                binding.imageNeedle.pivotX = 60f
                binding.imageNeedle.pivotY = 60f
                needleAnimator.duration = 500
                needleAnimator.interpolator = AccelerateInterpolator()
                needleAnimator.start()
            } else {
                binding.imageCurrent.setImageResource(R.drawable.pause_icon)
                animator?.pause()
                val needleAnimator = ObjectAnimator.ofFloat(binding.imageNeedle, "rotation", 0f, -15f)
                binding.imageNeedle.pivotX = 60f
                binding.imageNeedle.pivotY = 60f
                needleAnimator.duration = 500
                needleAnimator.interpolator = AccelerateInterpolator()
                needleAnimator.start()
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun getPlayProcess(event: PlayingEvent) {
        val second = event.current / 1000
        binding.playProcessBar.changeProcess(second.toInt())
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun beginPlay(event: BeginPlayEvent) {
        val duration = (event.songInfo.duration / 1000).toInt()
        binding.playProcessBar.initProcessBar(duration)
        binding.playProcessBar.setCanModify(true)
        if (PlayController.isPause()) {
            viewModel.isPlaying.postValue(false)
        } else {
            viewModel.isPlaying.postValue(true)
        }
        binding.toolbarTitle.text = event.songInfo.songName
        if (event.songInfo.songCover.isNotEmpty()) {
            Glide.with(this).asBitmap().load(event.songInfo.songCover).into(object : CustomViewTarget<ImageView, Bitmap>(binding.imageCenter) {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                }

                override fun onResourceCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val bitmap = BlurUtil.doBlur(resource, 10, 5)
                    binding.background.setImageBitmap(bitmap)
                    val rotateBitmap = BlurUtil.mergeThumbnailBitmap(resource, 700, 700)
                    binding.imageCenter.setImageBitmap(rotateBitmap)
                    animator = ObjectAnimator.ofFloat(binding.imageCenter, "rotation", 0f, 360.0f)
                    animator?.let {
                        it.duration = 20000
                        it.interpolator = LinearInterpolator() //匀速
                        it.repeatCount = -1 //设置动画重复次数（-1代表一直转）
                        it.repeatMode = ValueAnimator.RESTART //动画重复模式
                        it.start()
                        if (viewModel.isPlaying.value == false) {
                            it.pause()
                        }
                    }
                }
            })
        } else {
            val default: Bitmap = BitmapFactory.decodeResource(baseContext.resources, R.drawable.default_play)
            val bitmap = BlurUtil.doBlur(default, 22, 15)
            binding.background.setImageBitmap(bitmap)
            val rotateBitmap = BlurUtil.mergeThumbnailBitmap(default, 700, 700)
            binding.imageCenter.setImageBitmap(rotateBitmap)
            animator = ObjectAnimator.ofFloat(binding.imageCenter, "rotation", 0f, 360.0f)
            animator?.let {
                it.duration = 20000
                it.interpolator = LinearInterpolator() //匀速
                it.repeatCount = -1 //设置动画重复次数（-1代表一直转）
                it.repeatMode = ValueAnimator.RESTART //动画重复模式
                it.start()
                if (viewModel.isPlaying.value == false) {
                    it.pause()
                }
            }
        }

    }

    @Subscribe
    fun pause(event: PauseFinishEvent) {
        viewModel.isPlaying.postValue(false)
    }

    @Subscribe
    fun reStart(event: RestartFinishEvent) {
        viewModel.isPlaying.postValue(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish() }
        }
        return true
    }

}