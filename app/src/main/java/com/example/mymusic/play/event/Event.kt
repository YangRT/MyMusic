package com.example.mymusic.play.event

import com.lzx.starrysky.SongInfo

// 暂停
class PauseEvent

class PauseFinishEvent

// 下载
class DownloadEvent

class DownloadFinishEvent(val path: String)

// 继续播放
class RestartEvent

class RestartFinishEvent

// 下一首
class NextSongEvent

// 上一首
class PreSongEvent

// 添加到下一首播放
class NextPlayEvent(var songInfo: SongInfo)

class NextPlayFinishEvent

// 改变播放顺序
// REPEAT_MODE_NONE = 100     //顺序播放
// REPEAT_MODE_ONE = 200      //单曲播放
// REPEAT_MODE_SHUFFLE = 300  //随机播放
// REPEAT_MODE_REVERSE = 400  //倒序播放
class ChangePlayOrderEvent(var order: Int)

class ChangePlayOrderFinishEvent(var order: Int)

// 添加到播放历史
class AddToHistoryEvent(var info: SongInfo)

// 添加到播放列表
class AddSongToListEvent(var list: MutableList<SongInfo>)

// 保存播放列表
class SavePlayListEvent(var list: List<SongInfo>)

// 快进 快退
class SeekToEvent(var position: Long)

// 播放进度 总时长
class PlayingEvent(var duration: Long, var current: Long)

// 初始化
class BeginPlayEvent(var songInfo: SongInfo)

// 播放出错 或者 没有版权
class CanNotPlayEvent(var msg: String)

class ExitEvent()

class RefreshLoginStatusEvent()








