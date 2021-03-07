package com.example.mymusic.audio

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.MyApplication
import com.example.mymusic.R
import com.example.mymusic.databinding.ActivityAudioBinding
import com.iflytek.cloud.*
import com.iflytek.cloud.util.ResourceUtil
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE
import org.json.JSONException
import org.json.JSONObject

class AudioActivity : AppCompatActivity() {

    private val TAG = "AudioActivity"

    private lateinit var binding: ActivityAudioBinding
    private lateinit var viewModel: AudioViewModel

    // 语音唤醒对象
    private var mIvw: VoiceWakeuper? = null

    // 语音识别对象
    private var mAsr: SpeechRecognizer? = null

    // 唤醒结果内容
    private var resultString: String? = null

    // 识别结果内容
    private var recoString: String? = null

    // 设置门限值 ： 门限值越低越容易被唤醒
    private var tvThresh: TextView? = null
    private var seekbarThresh: SeekBar? = null
    private var MAX = 3000
    private var MIN = 0
    private var curThresh = 1450
    private var threshStr = "门限值："

    // 云端语法文件
    private var mCloudGrammar: String? = null

    // 云端语法id
    private var mCloudGrammarID: String? = null

    // 本地语法id
    private var mLocalGrammarID: String? = null

    // 本地语法文件
    private var mLocalGrammar: String? = null

    // 本地语法构建路径
    private val grmPath =
        (Environment.getExternalStorageDirectory().absolutePath
                + "/msc/test")

    // 引擎类型
    private val mEngineType = SpeechConstant.TYPE_LOCAL

    private var grammarListener = GrammarListener { grammarId, error ->
            if (error == null) {
                mLocalGrammarID = grammarId
                Toast.makeText(baseContext, "语法构建成功：$grammarId", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "语法构建失败,错误码：" + error.errorCode, Toast.LENGTH_SHORT).show()
            }
        }

    private val mWakeuperListener: WakeuperListener =
        object : WakeuperListener {
            override fun onResult(result: WakeuperResult) {
                try {
                    val text = result.resultString
                    val `object`: JSONObject
                    `object` = JSONObject(text)
                    val buffer = StringBuffer()
                    buffer.append("【RAW】 $text")
                    buffer.append("\n")
                    buffer.append("【操作类型】" + `object`.optString("sst"))
                    buffer.append("\n")
                    buffer.append("【唤醒词id】" + `object`.optString("id"))
                    buffer.append("\n")
                    buffer.append("【得分】" + `object`.optString("score"))
                    buffer.append("\n")
                    buffer.append("【前端点】" + `object`.optString("bos"))
                    buffer.append("\n")
                    buffer.append("【尾端点】" + `object`.optString("eos"))
                    resultString = buffer.toString()
                    Toast.makeText(baseContext, recoString, Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    resultString = "结果解析出错"
                    e.printStackTrace()
                }
                //textView.setText(resultString)
            }

            override fun onError(error: SpeechError) {
                Toast.makeText(baseContext, error.getPlainDescription(true), Toast.LENGTH_SHORT).show()
            }

            override fun onBeginOfSpeech() {
                Toast.makeText(baseContext, "开始说话", Toast.LENGTH_SHORT).show()
            }

            override fun onEvent(eventType: Int, isLast: Int, arg2: Int, obj: Bundle) {
                Log.d(
                    TAG,
                    "eventType:" + eventType + "arg1:" + isLast + "arg2:" + arg2
                )
                // 识别结果
                if (SpeechEvent.EVENT_IVW_RESULT == eventType) {
                    val reslut =
                        obj[SpeechEvent.KEY_EVENT_IVW_RESULT] as RecognizerResult
                    recoString += JsonParser.parseGrammarResult(reslut.resultString)
                }
            }

            override fun onVolumeChanged(volume: Int) {
                // TODO Auto-generated method stub
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_audio)
        binding.lifecycleOwner = this
        SpeechUtility.createUtility(baseContext, SpeechConstant.APPID +"="+R.string.app_id)
        viewModel = ViewModelProvider(this).get(AudioViewModel::class.java)
        initData()
    }

    private fun initData() {
        // 初始化唤醒对象
        mIvw = VoiceWakeuper.createWakeuper(this, null)
        // 初始化识别对象---唤醒+识别,用来构建语法
        mAsr = SpeechRecognizer.createRecognizer(this, null)
        // 初始化语法文件
        mCloudGrammar = readFile(this, "wake_grammar_sample.abnf")
        mLocalGrammar = readFile(this, "wake.bnf")
        mAsr?.let {
            if (mEngineType == SpeechConstant.TYPE_CLOUD) {
                // 设置参数
                it.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType)
                it.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8")
                // 设置语法构建路径
                it.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath)

                // 开始构建语法
                val ret = it.buildGrammar("abnf", mCloudGrammar, grammarListener)
                if (ret != ErrorCode.SUCCESS) {
                    Toast.makeText(baseContext, "语法构建失败,错误码：" + ret, Toast.LENGTH_SHORT).show()
                }
            } else {
                it.setParameter(SpeechConstant.PARAMS, null)
                it.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8")
                // 设置引擎类型
                it.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType)
                // 设置语法构建路径
                it.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath)
                // 设置资源路径
                it.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath())
                val ret = it.buildGrammar("bnf", mLocalGrammar, grammarListener)
                if (ret != ErrorCode.SUCCESS) {
                    Toast.makeText(baseContext, "语法构建失败了,错误码：" + ret, Toast.LENGTH_SHORT).show()
                }
            }
        }

        mIvw?.let {
            val resPath = ResourceUtil.generateResourcePath(
                this,
                RESOURCE_TYPE.assets,
                "ivw/" + getString(R.string.app_id) + ".jet"
            )
            // 设置业务类型为唤醒识别
            it.setParameter( SpeechConstant.IVW_SST,"oneshot")
            // 清空参数
            it.setParameter(SpeechConstant.PARAMS, null)
            // 设置识别引擎
            it.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType)
            // 设置唤醒资源路径
            it.setParameter(ResourceUtil.IVW_RES_PATH, resPath)
            /**
             * 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
             * 示例demo默认设置第一个唤醒词，建议开发者根据定制资源中唤醒词个数进行设置
             */
            it.setParameter(
                SpeechConstant.IVW_THRESHOLD, "0:"
                        + curThresh
            )
            // 设置唤醒+识别模式
            it.setParameter(SpeechConstant.IVW_SST, "oneshot")
            // 设置返回结果格式
            it.setParameter(SpeechConstant.RESULT_TYPE, "json")

//			mIvw.setParameter(SpeechConstant.IVW_SHOT_WORD, "0");

            // 设置唤醒录音保存路径，保存最近一分钟的音频
            it.setParameter(
                SpeechConstant.IVW_AUDIO_PATH,
                Environment.getExternalStorageDirectory().path + "/msc/ivw.wav"
            )
            it.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")

            if (!TextUtils.isEmpty(mLocalGrammarID)) {
                // 设置本地识别资源
                mIvw!!.setParameter(
                    ResourceUtil.ASR_RES_PATH,
                    getResourcePath()
                )
                // 设置语法构建路径
                mIvw!!.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath)
                // 设置本地识别使用语法id
                mIvw!!.setParameter(
                    SpeechConstant.LOCAL_GRAMMAR,
                    mLocalGrammarID
                )
                mIvw!!.startListening(mWakeuperListener)
            } else {
                Toast.makeText(baseContext, "请先构建语法", Toast.LENGTH_SHORT).show()
            }

        }
    }

    /**
     * 读取asset目录下文件。
     *
     * @return content
     */
    private fun readFile(
        mContext: Context,
        file: String
    ): String? {
        var len = 0
        var buf: ByteArray? = null
        var result = ""
        try {
            val `in` = mContext.assets.open(file)
            len = `in`.available()
            buf = ByteArray(len)
            `in`.read(buf, 0, len)
            result = String(buf, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    // 获取识别资源路径
    private fun getResourcePath(): String? {
        val tempBuffer = StringBuffer()
        // 识别通用资源
        tempBuffer.append(
            ResourceUtil.generateResourcePath(
                this,
                RESOURCE_TYPE.assets, "asr/common.jet"
            )
        )
        return tempBuffer.toString()
    }
}