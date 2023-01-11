package com.isgarsi.spanbbowl.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.ActivityMainBinding

class MainMenuActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnStartCGame.setOnClickListener(this)
        binding.btnDices.setOnClickListener(this)
        binding.btnSkills.setOnClickListener(this)
        binding.btnPasses.setOnClickListener(this)
        binding.btnTables.setOnClickListener(this)

        setContentView(binding.root)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnStartCGame -> startCompleteGame()
            R.id.btnDices -> startHelp(DICES)
            R.id.btnSkills -> startHelp(SKILLS)
            R.id.btnPasses -> startHelp(PASSES)
            R.id.btnTables -> startHelp(TABLES)
        }
    }

    private fun startHelp(option: Int) {
        val intent = Intent(this@MainMenuActivity, HelpActivity::class.java)
        intent.putExtra(ARG_NAME, option)
        startActivity(intent)
    }

    private fun startCompleteGame() {
        val intent = Intent(this@MainMenuActivity, GameActivity::class.java)
        startActivity(intent)
    }


}