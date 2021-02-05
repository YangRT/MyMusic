package com.example.mymusic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mymusic.base.BaseActivity

class FlashActivity : BaseActivity() {

    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WAKE_LOCK, Manifest.permission.READ_EXTERNAL_STORAGE)
    private val requestPerCode = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)
        val handler = Handler()
        handler.postDelayed(Runnable { checkPermission() }, 1200)
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，申请权限
            ActivityCompat.requestPermissions(this, permissions, requestPerCode);
        }else {
            //拥有权限
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPerCode && grantResults.size == 3
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
            && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else {
            Toast.makeText(this, "拒绝权限，无法使用！", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}