package com.laragh.autorepair.mechanic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.laragh.autorepair.databinding.ActivityMechanicBinding

class MechanicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMechanicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMechanicBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}