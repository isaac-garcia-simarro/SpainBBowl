package com.isgarsi.spanbbowl.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.FragmentLoginBinding
import com.isgarsi.spanbbowl.utils.snackBar

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private var mListener: ILoginFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)

        return binding.root
    }

    override fun onAttach(context: Context) {
        mListener = if(context is ILoginFragmentListener)  context else null
        super.onAttach(context)
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }




    override fun onClick(v: View) {
        when(v.id){
            R.id.btnLogin -> login()
            R.id.btnRegister -> mListener?.onClickRegister()
        }
    }

    private fun login(){
        val user: String = binding.etUsername.text.toString()
        val pass: String = binding.etPassword.text.toString()
        if(user.isNotEmpty() && pass.isNotEmpty()){
            mListener?.onClickLogin(user, pass)
        }else{
            activity?.snackBar(getString(R.string.invalid_username), binding.root)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    interface ILoginFragmentListener{
        fun onClickLogin(username: String, password: String)
        fun onClickRegister()
    }
}