package com.isgarsi.spanbbowl.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.ActivityGameBinding
import com.isgarsi.spanbbowl.databinding.ActivityLoginBinding
import com.isgarsi.spanbbowl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    
}