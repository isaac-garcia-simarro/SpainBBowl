package com.isgarsi.spanbbowl.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.isgarsi.spanbbowl.databinding.ActivityGameBinding
import com.isgarsi.spanbbowl.databinding.ActivityLoginBinding
import com.isgarsi.spanbbowl.databinding.ActivityMainBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}