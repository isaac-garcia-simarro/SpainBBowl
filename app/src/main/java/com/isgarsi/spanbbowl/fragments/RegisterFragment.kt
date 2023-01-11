package com.isgarsi.spanbbowl.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.FragmentLoginBinding
import com.isgarsi.spanbbowl.databinding.FragmentRegisterBinding
import com.isgarsi.spanbbowl.utils.snackBar

class RegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentRegisterBinding
    private var mListener: IRegisterFragmentListener? = null
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        binding.buttonSignUp.setOnClickListener(this)
        binding.buttonGoLogIn.setOnClickListener(this)

        return binding.root
    }

    override fun onAttach(context: Context) {
        mListener = if(context is IRegisterFragmentListener)  context else null
        super.onAttach(context)
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.buttonSignUp -> register()
            R.id.buttonGoLogIn -> mListener?.onClickBack()
        }
    }

    private fun register(){
        val user: String = binding.editTextEmail.text.toString()
        val pass: String = binding.editTextPassword.text.toString()
        val pass2: String = binding.editTextConfirmPassword.text.toString()
        if(user.isNotEmpty() && pass.isNotEmpty()){
            if(pass == pass2){
                mListener?.register(user, pass)
            }else{
                activity?.snackBar(getString(R.string.invalid_passwords), binding.root)
            }
        }else{
            activity?.snackBar(getString(R.string.invalid_username), binding.root)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }

    interface IRegisterFragmentListener{
        fun register(email: String, password: String)
        fun onClickBack()
    }


}