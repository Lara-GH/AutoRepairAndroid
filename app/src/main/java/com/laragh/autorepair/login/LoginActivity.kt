package com.laragh.autorepair.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laragh.autorepair.R
import com.laragh.autorepair.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.container, SignInFragment()).commit()
    }
}