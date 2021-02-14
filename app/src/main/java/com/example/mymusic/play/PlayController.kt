package com.example.mymusic.play

import com.example.mymusic.play.event.*
import com.example.mymusic.play.interceptor.CheckMusicInterceptor
import com.example.mymusic.play.interceptor.GetPlayUrlInterceptor
import com.example.mymusic.play.interceptor.SavePlayInfoInterceptor
import com.lzx.starrysky.OnPlayProgressListener
import com.lzx.starrysky.OnPlayerEventListener
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.manager.PlaybackStage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

// 音乐播放控制
object PlayController {

    private var isPause = false

    init {
        EventBus.getDefault().register(this)
        StarrySky.with().addPlayerEventListener(object : OnPlayerEventListener {
            override fun onPlaybackStageChange(stage: PlaybackStage) {
                when (stage.stage) {
                    PlaybackStage.IDEA -> {

                    }
                    PlaybackStage.ERROR -> {
                        stage.errorMsg?.let {
                            if (it.startsWith("ExoPlayer error")) {
                                EventBus.getDefault().post(CanNotPlayEvent("网络不给力"))
                            } else {
                                EventBus.getDefault().post(CanNotPlayEvent(it))
                            }

                        }
                        if (stage.errorMsg == null) {
                            EventBus.getDefault().post(CanNotPlayEvent("未知错误"))
                        }
                    }
                    PlaybackStage.PAUSE -> {
                        EventBus.getDefault().post(PauseFinishEvent())
                        isPause = true
                    }
                    PlaybackStage.SWITCH -> {
                        isPause = false
                        stage.songInfo?.let {
                            EventBus.getDefault().postSticky(BeginPlayEvent(it))
                        }
                    }
                    PlaybackStage.BUFFERING -> {
                        EventBus.getDefault().post(CanNotPlayEvent("正在加载..."))
                    }
                    PlaybackStage.PLAYING -> {
                        if (isPause) {
                            isPause = false
                            EventBus.getDefault().post(RestartFinishEvent())
                        } else {
                            stage.songInfo?.let {
                                EventBus.getDefault().postSticky(BeginPlayEvent(it))
                            }
                        }
                    }
                }
            }

        }, "control")
        StarrySky.with().setOnPlayProgressListener(object : OnPlayProgressListener {
            override fun onPlayProgress(currPos: Long, duration: Long) {
                EventBus.getDefault().postSticky(PlayingEvent(duration, currPos))
            }
        })
    }

    @Subscribe
    fun nextSong(event: NextSongEvent) {
        if (!StarrySky.with().isSkipToNextEnabled()) {
            EventBus.getDefault().post(CanNotPlayEvent("没有下一首!"))
            return
        }
        StarrySky.with().skipToNext()
    }

    @Subscribe
    fun previousSong(event: PreSongEvent) {
        if (!StarrySky.with().isSkipToPreviousEnabled()) {
            EventBus.getDefault().post(CanNotPlayEvent("没有上一首！"))
            return
        }
        StarrySky.with().skipToPrevious()
    }

    @Subscribe
    fun pausePlay(event: PauseEvent) {
        StarrySky.with().pauseMusic()
    }

    @Subscribe
    fun restartPlay(event: RestartEvent) {
        StarrySky.with().restoreMusic()
    }

    @Subscribe
    fun seekToWhen(event: SeekToEvent) {
        StarrySky.with().seekTo(event.position, true)
    }

    @Subscribe
    fun setRepeatMode(event: ChangePlayOrderEvent) {
        StarrySky.with().setRepeatMode(event.order, true)
    }

    @Subscribe
    fun addSongToList(event: AddSongToListEvent) {
        StarrySky.with().addPlayList(event.list)
    }

    @Subscribe
    fun addToNextPlay(event: NextPlayEvent) {
        StarrySky.with().addSongInfo(0, event.songInfo)
    }

    fun playNow(songInfo: SongInfo) {
        val index  = StarrySky.with().getNowPlayingIndex()
        if (index == -1) {
            StarrySky.with().playMusicByInfo(songInfo)
        } else {
            StarrySky.with().addSongInfo(index + 1, songInfo)
            StarrySky.with().playMusicByInfo(songInfo)
        }
    }

    fun playAll(songs: MutableList<SongInfo>) {
        if (songs.isEmpty()) return
        var index  = StarrySky.with().getNowPlayingIndex()
        if (index == -1) {
            StarrySky.with().playMusic(songs, 0)
        } else {
            for (song in songs) {
                StarrySky.with().addSongInfo( index + 1, song)
                index++
            }
            StarrySky.with().playMusicByInfo(songs[0])
        }
    }

    fun isPause(): Boolean {
        return isPause
    }

}