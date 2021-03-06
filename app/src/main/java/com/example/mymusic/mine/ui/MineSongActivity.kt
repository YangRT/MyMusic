package com.example.mymusic.mine.ui

import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityMineSongBinding
import com.example.mymusic.mine.viewmodel.MineMusicViewModel
import com.example.mymusic.play.PlayController
import com.example.mymusic.play.PlayMusicActivity
import com.lzx.starrysky.SongInfo


class MineSongActivity : BaseActivity() {

    private lateinit var binding: ActivityMineSongBinding
    private lateinit var viewModel: MineMusicViewModel
    private lateinit var adapter: MineMusicAdapter
    private var list = ArrayList<SongInfo>()
    private var type: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mine_song)
        viewModel =  ViewModelProvider(this).get(MineMusicViewModel::class.java)
        type = intent.getIntExtra("type", -1)
        if (type < 0) {
            finish()
        }
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbarPlayRecord)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorMain), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        if (type == 0) {
            binding.toolbarTitle.setText(R.string.play_record)
        } else if (type == 1) {
            binding.toolbarTitle.setText(R.string.local_music)
        } else if (type == 2) {
            binding.toolbarTitle.setText(R.string.download_music)
        }
        list = getData()
        adapter = MineMusicAdapter(list)
        binding.playRecordRecyclerView.adapter = adapter
        binding.playRecordRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setEmptyView(R.layout.item_empty_view)
        binding.platRecordRefreshLayout.setOnRefreshListener {
            list = getData()
            adapter.setList(list)
            binding.platRecordRefreshLayout.isRefreshing = false
            Toast.makeText(this, R.string.refresh_success, Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            val info = list[position]
            PlayController.playNow(info)
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(this, PlayMusicActivity::class.java)
                startActivity(intent)
            }, 1500)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> { finish() }
        }
        return true
    }

    private fun getData(): ArrayList<SongInfo> {
        if (type == 0) {
            return viewModel.getRecordList()
        } else if (type == 1) {
            return getLocalMusic()
        } else if (type == 2) {
            return viewModel.getDownloadList()
        }
        return ArrayList()
    }

    private fun getLocalMusic(): ArrayList<SongInfo> {
        val list = ArrayList<SongInfo>()
        val cursor: Cursor? = baseContext.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
        cursor?.let {
            val nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val pathIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val singer = cursor.getString(artistIndex)
                val path = cursor.getString(pathIndex)
                val duration = cursor.getInt(durationIndex)
                val id = cursor.getLong(idIndex)
                if (duration <= 0 || !path.endsWith(".mp3")) {
                    continue
                }
                val songInfo = SongInfo()
                songInfo.artist = singer
                songInfo.duration = duration.toLong()
                songInfo.songName = name
                songInfo.songUrl = path
                songInfo.songId = id.toString()
                songInfo.coverBitmap = getDefaultArtwork()
                list.add(songInfo)
            }
            cursor.close()
        }
        return list
    }

    private fun getDefaultArtwork(): Bitmap {
        val opts = BitmapFactory.Options()
        opts.inPreferredConfig = Bitmap.Config.RGB_565
        return BitmapFactory.decodeResource(baseContext.resources, R.drawable.logo)
    }
}