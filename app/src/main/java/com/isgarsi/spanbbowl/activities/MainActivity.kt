package com.isgarsi.spanbbowl.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.igs.mylibrary.activities.ToolbarActivity
import com.igs.passwordmanager.listeners.RecyclerMainActivityListener
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.adapters.MainMenuItemsAdapter
import com.isgarsi.spanbbowl.databinding.ActivityMainBinding
import com.isgarsi.spanbbowl.fragments.*
import com.isgarsi.spanbbowl.models.MainMenuItem
import com.isgarsi.spanbbowl.utils.fragmentTransactionBackStack
import com.isgarsi.spanbbowl.utils.fragmentTransactionNoBackStack

class MainActivity : ToolbarActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    MainMenuFragment.IMainMenuFragmentListener{

    private lateinit var binding: ActivityMainBinding
    private var anyOptionSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setNavDrawer()
        setContentView(binding.root)

        fragmentTransactionNoBackStack(supportFragmentManager, MainMenuFragment.newInstance(), R.id.container)
    }

    private fun setNavDrawer() {
        toolbarToLoad(binding.toolbarView as Toolbar?)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, _toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        toggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        val tvDisplayName: TextView = binding.navView.getHeaderView(0).findViewById(R.id.navTextViewEmail)
        tvDisplayName.text = FirebaseAuth.getInstance().currentUser?.email

        binding.navView.menu.getItem(0).isChecked = true

        //Listeners logout option
        binding.ivLogout.setOnClickListener { logOut() }
        binding.tvLogout.setOnClickListener { logOut() }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var f: Fragment
        when (item.itemId) {
            R.id.nav_timer -> {
                if (!binding.navView.menu.getItem(0).isChecked) {
                    f = ShiftsFragment.newInstance()
                }
            }
            R.id.nav_dices -> {
                if (!binding.navView.menu.getItem(1).isChecked) {
                    f = DicesFragment.newInstance()
                }
            }
            R.id.nav_skills -> {
                if (!binding.navView.menu.getItem(2).isChecked) {
                    f = SkillsFragment.newInstance()
                }
            }
            R.id.nav_tables -> {
                if (!binding.navView.menu.getItem(3).isChecked) {
                    f = TablesFragment.newInstance()
                }
            }
        }

        changeFragment(f)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        anyOptionSelected = false
        super.onBackPressed()
    }


    private fun logOut() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        FirebaseAuth.getInstance().signOut()
        var intent: Intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onClickMenuItem(item: MainMenuItem) {
        lateinit var f: Fragment
        when (item.position) {
            MM_SHIFTS -> f = ShiftsFragment.newInstance()
            MM_DICES -> f = DicesFragment.newInstance()
            MM_SKILLS -> f = SkillsFragment.newInstance()
            MM_TABLES -> f = TablesFragment.newInstance()
        }
        changeFragment(f)
    }

    fun changeFragment(f: Fragment){
        //Only the First time, save in backstack
        if(anyOptionSelected) {
            fragmentTransactionNoBackStack(supportFragmentManager, f, R.id.container)
        }else{
            fragmentTransactionBackStack(supportFragmentManager, f, R.id.container)
            anyOptionSelected = true
        }
    }
}