package com.isgarsi.spanbbowl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.isgarsi.spanbbowl.R

class DicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dices, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = DicesFragment()
    }
}