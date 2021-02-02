package com.example.mymusic.search.record

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.IBinder
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RecordService : Service() {

    //val voicePath = Environment.getExternalStorageDirectory().toString() + "/myPrivateRecord"


    private lateinit var voiceDictionaryPath: String

    private var mRecorder: MediaRecorder? = null
    /**
     * 录音开始时间
     */
    private var startTime: Long = 0
    /**
     * 录音生产的文件路径
     */
    private var voicePath: String? = null

    /**
     * 录音生产的文件名
     */
    private var voiceName: String? = null

    /**
     * 录音时间 从开始录音到停止时的时间
     */
    private var voiceTime: Long = 0

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        voiceDictionaryPath = baseContext.filesDir.toString() + "/myRecord/"
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startRecord()
        return START_STICKY
    }

    override fun onDestroy() {
        if (mRecorder != null) {
            stopVoice()
        }
        super.onDestroy()
    }

    private fun startRecord() {
        createVoiceNameAndPath()
        try {
            mRecorder = MediaRecorder()
            //设置声音来源
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
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
            //记录开始时间
            startTime = System.currentTimeMillis()
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


    private fun stopVoice() {
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

        //获取录制时间
        voiceTime = System.currentTimeMillis() - startTime

        saveToDb()
    }

    private fun saveToDb() {
//        val voice = Voice()
//        voice.recordTime = TimeUtils.getCurrentTime()
//        voice.voiceTime = TimeUtils.getRecordTimeByMill(voiceTime)
//        voice.voiceName = voiceName
//        voice.voicePath = voicePath
//        VoiceHelper.instance.insertData(voice)
    }


}