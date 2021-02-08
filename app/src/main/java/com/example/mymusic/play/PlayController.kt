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

    init {
        EventBus.getDefault().register(this)
        StarrySky.with().addPlayerEventListener(object : OnPlayerEventListener{
            override fun onPlaybackStageChange(stage: PlaybackStage) {

            }

        }, "control")
        StarrySky.with().setOnPlayProgressListener(object: OnPlayProgressListener{
            override fun onPlayProgress(currPos: Long, duration: Long) {
                EventBus.getDefault().post(PlayingEvent(duration, currPos))
            }
        })
    }

    @Subscribe
    fun nextSong(event: NextSongEvent) {
        StarrySky.with().skipToNext()
    }

    @Subscribe
    fun previousSong(event: PreSongEvent) {
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
        StarrySky.with().playMusicByInfo(songInfo)
    }

}