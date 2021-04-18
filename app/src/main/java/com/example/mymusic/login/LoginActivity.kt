package com.example.mymusic.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.MainActivity
import com.example.mymusic.R
import com.example.mymusic.base.BaseActivity
import com.example.mymusic.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initView()
        viewModel.loginStatus.observe(this, Observer {
            if (it) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }, 100)
            }
        })
    }

    private fun initView() {
        binding.login.setOnClickListener {
            val phone = binding.inputPhone.text.toString()
            val password = binding.inputPassword.text.toString()
            viewModel.login(phone, password)
        }
        binding.noRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("type",0)
            startActivity(intent)
        }
        binding.forgetPassword.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("type",1)
            startActivity(intent)
        }
    }

}