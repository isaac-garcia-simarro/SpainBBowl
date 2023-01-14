package com.isgarsi.spanbbowl.fragments

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.FragmentDicesBinding
import com.isgarsi.spanbbowl.databinding.FragmentLoginBinding

class DicesFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDicesBinding.inflate(layoutInflater, container, false)
        binding.imageViewD2.setOnClickListener(this)
        binding.imageViewD6.setOnClickListener(this)
        binding.imageViewD8.setOnClickListener(this)
        binding.imageViewD12.setOnClickListener(this)
        binding.imageViewD16.setOnClickListener(this)
        binding.imageViewDTackle.setOnClickListener(this)
        binding.imageViewDTackle2.setOnClickListener(this)
        binding.imageViewDTackle3.setOnClickListener(this)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = DicesFragment()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imageViewD2 -> rollANumericDice(2)
            R.id.imageViewD6 -> rollANumericDice(6)
            R.id.imageViewD8 -> rollANumericDice(8)
            R.id.imageViewD12 -> rollANumericDice(12)
            R.id.imageViewD16 -> rollANumericDice(16)
            R.id.imageViewDTackle -> rollBlockDices(1)
            R.id.imageViewDTackle2 -> rollBlockDices(2)
            R.id.imageViewDTackle3 -> rollBlockDices(3)
        }
    }

    private fun showHideResultsView(blockDices: Boolean = false, quantityOfBlockDices: Int = 0){
        if(blockDices){
            binding.blockLayout.visibility = View.VISIBLE
            binding.textViewRes.visibility = View.GONE
            //Show and hide the necessary views to show the block dices
            when(quantityOfBlockDices){
                1 -> {
                    binding.imageViewResultBlock1.visibility = View.INVISIBLE
                    binding.imageViewResultBlock2.visibility = View.VISIBLE
                    binding.imageViewResultBlock3.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.imageViewResultBlock1.visibility = View.VISIBLE
                    binding.imageViewResultBlock2.visibility = View.INVISIBLE
                    binding.imageViewResultBlock3.visibility = View.VISIBLE
                }
                3 -> {
                    binding.imageViewResultBlock1.visibility = View.VISIBLE
                    binding.imageViewResultBlock2.visibility = View.VISIBLE
                    binding.imageViewResultBlock3.visibility = View.VISIBLE
                }
            }
        }else{
            binding.blockLayout.visibility = View.GONE
            binding.textViewRes.visibility = View.VISIBLE
        }
    }

    private fun rollBlockDices(quantity: Int){
        showHideResultsView(true, quantity)

        var dices = IntArray(quantity)
        for(i in 0 until quantity){
            dices[i] = (1..6).random()
        }

        showBlockDices(dices)
    }

    private fun showBlockDices(mDices:IntArray) {
        when(mDices.size){
            1 -> {
                showBlockDice(mDices[0], binding.imageViewResultBlock2)
            }
            2 -> {
                showBlockDice(mDices[0], binding.imageViewResultBlock1)
                showBlockDice(mDices[1], binding.imageViewResultBlock3)
            }
            3 -> {
                showBlockDice(mDices[0], binding.imageViewResultBlock1)
                showBlockDice(mDices[1], binding.imageViewResultBlock2)
                showBlockDice(mDices[2], binding.imageViewResultBlock3)
            }
        }
    }

    private fun showBlockDice(diceResult: Int, im: ImageView) {
        when(diceResult){
            1,6 -> im.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.push_back))
            2 -> im.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.defender_stumbles))
            3 -> im.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.attacker_down))
            4 -> im.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.both_down))
            5 -> im.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.defender_down))
        }
    }

    private fun rollANumericDice(max: Int){
        val res = (1..max).random()
        showNumericDice(res)
    }

    private fun showNumericDice(res: Int) {
        showHideResultsView()
        binding.textViewRes.text = "$res"
    }
}