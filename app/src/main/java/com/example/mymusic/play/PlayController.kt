package com.example.mymusic.play

import com.example.mymusic.play.db.PlayedSongDatabase
import com.example.mymusic.play.event.*
import com.lzx.starrysky.OnPlayProgressListener
import com.lzx.starrysky.OnPlayerEventListener
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
        // 利用插件添加播放记录
        StarrySky.addInterceptor(SavePlayInfoInterceptor())
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
    fun addToNextPlay(event: NextSongEvent) {
        StarrySky.with().addSongInfo(0, event.songInfo)
    }


}