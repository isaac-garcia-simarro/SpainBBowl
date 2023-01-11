package com.isgarsi.spanbbowl.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.ActivityLoginBinding
import com.isgarsi.spanbbowl.fragments.LoginFragment
import com.isgarsi.spanbbowl.fragments.RegisterFragment
import com.isgarsi.spanbbowl.utils.fragmentTransactionBackStack
import com.isgarsi.spanbbowl.utils.fragmentTransactionNoBackStack
import com.isgarsi.spanbbowl.utils.snackBar


class LoginActivity : AppCompatActivity(),
    LoginFragment.ILoginFragmentListener,
    RegisterFragment.IRegisterFragmentListener{

    private lateinit var binding: ActivityLoginBinding
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() };

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        // Check if user is signed in (non-null) and update UI accordingly.
        if(currentUser != null){
            //User logged
            // Go to main activity
            goToMain()
        }else{//Login fragment
            fragmentTransactionNoBackStack(
                supportFragmentManager,
                LoginFragment.newInstance(),
                R.id.container)
        }
    }

    private fun goToMain(){
        startActivity(Intent(this, MainMenuActivity::class.java))
    }


    /* *****************************************
     *  ILoginFragmentListener
     *  **************************************** */
    override fun onClickLogin(username: String, password: String) {
        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMain()
                } else {
                    snackBar(getString(R.string.login_sign_in_error_user_msg), binding.root)
                }
            }
        mAuth.signInWithEmailAndPassword(username, password)
    }

    override fun onClickRegister() {
        fragmentTransactionBackStack(
            supportFragmentManager,
            RegisterFragment.newInstance(),
            R.id.container)
    }

    /* *****************************************
     *  IRegisterFragmentListener
     *  **************************************** */
    override fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this) {
                    snackBar(getString(R.string.login_sign_up_correctly_msg), binding.root)
                    goToMain()
                }
            } else {
                snackBar(getString(R.string.login_sign_up_error_msg), binding.root)
            }
        }
    }

    override fun onClickBack() {
        onBackPressed()
    }
}