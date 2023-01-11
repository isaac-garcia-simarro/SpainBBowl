package com.isgarsi.spanbbowl.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.fragments.*
import com.isgarsi.spanbbowl.utils.fragmentTransactionNoBackStack

const val DICES = 1
const val SKILLS = 1
const val PASSES = 3
const val TABLES = 4
const val ARG_NAME = "FRAGMENT_TYPE";

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val fragmentType= intent.getIntExtra(ARG_NAME, DICES) //DEFAULT DICES FRAGMENT
        loadFragment(fragmentType)
    }

    private fun loadFragment(type: Int){

        when(type){
            DICES -> fragmentTransactionNoBackStack(
                supportFragmentManager,
                DicesFragment.newInstance(),
                R.id.container)
            SKILLS -> fragmentTransactionNoBackStack(
                supportFragmentManager,
                SkillsFragment.newInstance(),
                R.id.container)
            PASSES -> fragmentTransactionNoBackStack(
                supportFragmentManager,
                PassesFragment.newInstance(),
                R.id.container)
            TABLES -> fragmentTransactionNoBackStack(
                supportFragmentManager,
                TablesFragment.newInstance(),
                R.id.container)
        }
    }
}