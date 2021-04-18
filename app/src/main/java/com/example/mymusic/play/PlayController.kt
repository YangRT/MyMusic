package com.example.mymusic.play

import android.os.Environment
import android.util.Log
import com.example.mymusic.play.db.DownloadSongInfo
import com.example.mymusic.play.db.PlayedSongDatabase
import com.example.mymusic.play.event.*
import com.example.mymusic.play.interceptor.CheckMusicInterceptor
import com.example.mymusic.play.interceptor.GetPlayUrlInterceptor
import com.example.mymusic.play.interceptor.SaveDownloadSongInfoInterceptor
import com.example.mymusic.play.interceptor.SavePlayInfoInterceptor
import com.example.mymusic.utils.DownLoadUtil
import com.lzx.starrysky.OnPlayProgressListener
import com.lzx.starrysky.OnPlayerEventListener
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.manager.PlaybackStage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File

// 音乐播放控制
object PlayController {

    private var isPause = false
    private var totalProcess = -1

    private val callBack = object : DownLoadUtil.DownCallBack{
        override fun success(file: File) {
            EventBus.getDefault().post(CanNotPlayEvent("下载成功"))
        }

        override fun fail(str: String) {
            EventBus.getDefault().post(CanNotPlayEvent("下载失败：$str"))

        }

        override fun progress(position: Long) {
            val current = (position/1000).toInt()
            EventBus.getDefault().post(CanNotPlayEvent("下载中：$current / $totalProcess"))

        }

        override fun total(total: Long) {
            totalProcess = (total/1000).toInt()
        }

    }

    fun init() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
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
                Log.e("PlayMusic", "currPos: $currPos  duration: $duration")
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

    @Subscribe
    fun downloadMusic(event: DownloadEvent) {
        val info = StarrySky.with().getNowPlayingSongInfo()
        info?.let {
            val url = Environment.getExternalStorageDirectory().absolutePath + "/myMusic/"+ info.songName + ".mp3"
            val list = PlayedSongDatabase.instance.downloadSongDao().query(it.songId)
            if (list.isNotEmpty() || File(url).exists()) {
                EventBus.getDefault().post(CanNotPlayEvent("歌曲已经下载!"))
                val downloadSongInfo = DownloadSongInfo(info.songId, info.songName, info.artist, info.duration, info.songCover, false, url)
                val dao = PlayedSongDatabase.instance.downloadSongDao()
                dao.insert(downloadSongInfo)
                DownLoadUtil.getInstance().refreshMedia(url)
            } else {
                val infoInterceptor = SaveDownloadSongInfoInterceptor(it, url)
                DownLoadUtil.getInstance().setInterceptor(infoInterceptor)
                DownLoadUtil.getInstance().initUrl(info.songUrl, null)
                DownLoadUtil.getInstance().setFileName(info.songName + ".mp3")
                DownLoadUtil.getInstance().setFilePath(Environment.getExternalStorageDirectory().absolutePath + "/myMusic/")
                DownLoadUtil.getInstance().setDownCallBack(callBack)
                DownLoadUtil.getInstance().down()
            }
        }
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