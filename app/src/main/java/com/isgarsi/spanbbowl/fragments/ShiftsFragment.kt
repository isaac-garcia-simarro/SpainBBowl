package com.isgarsi.spanbbowl.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.databinding.FragmentShiftsBinding


private const val PLAYER1 = 1
private const val PLAYER2 = 2
private const val MAX_SHIFTS = 8

class ShiftsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentShiftsBinding
    private lateinit var cardViewP1: CardView
    private lateinit var textNameP1: TextView
    private lateinit var textTimeP1: TextView
    private lateinit var textShiftP1: TextView
    private lateinit var cardViewP2: CardView
    private lateinit var textNameP2: TextView
    private lateinit var textTimeP2: TextView
    private lateinit var textShiftP2: TextView

    private var shiftP1 = 0
    private var shiftP2 = 0
    private var currentShift = PLAYER1
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShiftsBinding.inflate(layoutInflater, container, false)

        //Get references
        binding.cardViewPlayer1.setOnClickListener(this)
        binding.cardViewPlayer2.setOnClickListener(this)
        cardViewP1 = binding.cardViewPlayer1
        textNameP1 = cardViewP1.findViewById(R.id.textViewNameP1)
        textTimeP1 = cardViewP1.findViewById(R.id.textViewTimeP1)
        textShiftP1 = cardViewP1.findViewById(R.id.textViewShiftP1)
        cardViewP2 = binding.cardViewPlayer2
        textNameP2 = cardViewP2.findViewById(R.id.textViewNameP2)
        textTimeP2 = cardViewP2.findViewById(R.id.textViewTimeP2)
        textShiftP2 = cardViewP2.findViewById(R.id.textViewShiftP2)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShiftsFragment()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.cardViewPlayer1 -> changeShift(PLAYER1)
            R.id.cardViewPlayer2 -> changeShift(PLAYER2)
        }
    }

    private fun changeShift(player: Int) {
        if(shiftP1 == MAX_SHIFTS && shiftP2 == MAX_SHIFTS){
            Toast.makeText(context, "Game finished", Toast.LENGTH_SHORT).show()//TODO
        }else {
            //we assume it is player 1
            var backgroundColorP1 = R.color.primary_variant
            var textColorP1 = R.color.background
            var backgroundColorP2 = R.color.background
            var textColorP2 = R.color.primary_variant
            shiftP1++;
            currentShift = PLAYER1

            //Change if it is player2
            if (player == PLAYER2) {
                backgroundColorP1 = R.color.background
                textColorP1 = R.color.primary_variant
                backgroundColorP2 = R.color.primary_variant
                textColorP2 = R.color.background
                shiftP2++;
                shiftP1--;
                currentShift = PLAYER2
            }

            //Update P1
            cardViewP1.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    backgroundColorP1
                )
            )
            textNameP1.setTextColor(ContextCompat.getColor(requireContext(), textColorP1))
            textTimeP1.setTextColor(ContextCompat.getColor(requireContext(), textColorP1))
            textShiftP1.setTextColor(ContextCompat.getColor(requireContext(), textColorP1))
            textShiftP1.text = getString(R.string.shift) + " $shiftP1"

            //Update P2
            cardViewP2.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    backgroundColorP2
                )
            )
            textNameP2.setTextColor(ContextCompat.getColor(requireContext(), textColorP2))
            textTimeP2.setTextColor(ContextCompat.getColor(requireContext(), textColorP2))
            textShiftP2.setTextColor(ContextCompat.getColor(requireContext(), textColorP2))
            textShiftP2.text = getString(R.string.shift) + " $shiftP2"

            //Create the timer

        }
    }

//    textTimeP1.text = String.format("%02d:%02d",
//            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
//            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
//                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))))


}