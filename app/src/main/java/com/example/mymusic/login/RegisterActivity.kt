package com.example.mymusic.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymusic.R
import com.example.mymusic.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        initView()
        viewModel.registerStatus.observe(this, Observer {
            if (it) {
                finish()
            }
        })
        val type = intent.getIntExtra("type",0)
        if (type == 0) {
            binding.register.setText(R.string.register)
        } else {
            binding.register.setText(R.string.reset_password)
        }
    }

    private fun initView() {
        binding.getCaptcha.setOnClickListener {
            val phone = binding.inputPhone.text.toString()
            if (phone.isNotEmpty()) {
                viewModel.getCaptcha(phone)
            } else {
                Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show()
            }
        }
        binding.register.setOnClickListener {
            val phone = binding.inputPhone.text.toString()
            val captcha = binding.inputCaptcha.text.toString()
            val nickName = binding.inputNickname.text.toString()
            val password = binding.inputPassword.text.toString()
            if (phone.isEmpty() || captcha.isEmpty() || nickName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请补全信息！", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.register(phone, password, captcha, nickName)
            }

        }
    }

}