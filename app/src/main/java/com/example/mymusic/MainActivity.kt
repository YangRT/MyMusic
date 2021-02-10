package com.example.mymusic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.customview.PlayBottomWindow
import com.example.mymusic.databinding.ActivityMainBinding
import com.example.mymusic.find.ui.FindFragment
import com.example.mymusic.mine.MineFragment
import com.example.mymusic.play.event.*
import com.example.mymusic.search.ui.SearchActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseActivity(),Observer<Int>, View.OnClickListener {

    private val TAG = "HomeActivity"

    private lateinit var binding:ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private val findFragment = FindFragment()
    private val mineFragment = MineFragment()
    private lateinit var from :Fragment
    private lateinit var bottomWindow: PlayBottomWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        initView()
        EventBus.getDefault().register(this)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed( {
            val builder = PlayBottomWindow.ConfirmPopupWindowBuilder(this)
            bottomWindow = PlayBottomWindow(this, builder)
            bottomWindow.show()
        }, 1000)
    }

    override fun onChanged(t: Int?) {

    }

    //
    override fun onClick(v: View?) {
        var fragment:Fragment? = null
        when(v?.id){
            R.id.main_menu -> {

            }
            R.id.main_search -> {
                val intent = Intent(this,SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.main_tv_find -> {
                fragment = findFragment
                viewModel.selectedFirst.postValue(true)

            }
            R.id.main_tv_me -> {
                fragment = mineFragment
                viewModel.selectedFirst.postValue(false)

            }
        }
        fragment?.let { i ->
            if(i == from){
                return
            }
            switchFragment(from, i)
            from = i
        }
    }

    private fun initView(){
        binding.mainTvFind.setOnClickListener(this)
        binding.mainTvMe.setOnClickListener(this)
        binding.mainMenu.setOnClickListener(this)
        binding.mainSearch.setOnClickListener(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_fragment_container, findFragment).commit()
        from = findFragment
    }

    private fun switchFragment(from: Fragment, to:Fragment){
        if(from != to){
            val transaction = supportFragmentManager.beginTransaction()
            if(!to.isAdded){
                transaction.hide(from).add(R.id.main_fragment_container,to).commit()
            }else{
                transaction.hide(from)
                transaction.show(to).commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun canNotPlayMusic(event: CanNotPlayEvent) {
        Toast.makeText(this, event.msg, Toast.LENGTH_SHORT).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun pauseFinish(event: PauseFinishEvent) {
        bottomWindow.pause()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reStartFinish(event: RestartFinishEvent) {
        bottomWindow.restart()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun switchSong(event: BeginPlayEvent) {
        bottomWindow.switchSong(event.songInfo)
    }

}