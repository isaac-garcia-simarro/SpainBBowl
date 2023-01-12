package com.isgarsi.spanbbowl.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.isgarsi.spanbbowl.R

class SplashActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 2000 // 2 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val mAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            var intent: Intent
            if(mAuth.currentUser == null){
                intent = Intent(this, LoginActivity::class.java)
            }else{
                intent = Intent(this, MainActivity::class.java)
            }

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }, SPLASH_TIME_OUT)
    }
}