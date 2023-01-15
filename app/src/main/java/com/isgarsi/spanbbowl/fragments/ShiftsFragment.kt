package com.isgarsi.spanbbowl.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.activities.MainActivity
import com.isgarsi.spanbbowl.activities.SettingsActivity
import com.isgarsi.spanbbowl.databinding.FragmentShiftsBinding
import com.isgarsi.spanbbowl.utils.CustomCountDownTimer
import com.isgarsi.spanbbowl.utils.vibratePhone
import java.util.concurrent.TimeUnit


private const val PLAYER1 = 1
private const val PLAYER2 = 2

class ShiftsFragment : Fragment(), View.OnClickListener,
    CustomCountDownTimer.ICustomCountDownTimer{

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
    private var mTimer: CustomCountDownTimer? = null

    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private var maxShifts = 8
    private var minutesPerShift = 4L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShiftsBinding.inflate(layoutInflater, container, false)

        //Get references
        cardViewP1 = binding.cardViewPlayer1
        textNameP1 = cardViewP1.findViewById(R.id.textViewNameP1)
        textTimeP1 = cardViewP1.findViewById(R.id.textViewTimeP1)
        textShiftP1 = cardViewP1.findViewById(R.id.textViewShiftP1)
        cardViewP2 = binding.cardViewPlayer2
        textNameP2 = cardViewP2.findViewById(R.id.textViewNameP2)
        textTimeP2 = cardViewP2.findViewById(R.id.textViewTimeP2)
        textShiftP2 = cardViewP2.findViewById(R.id.textViewShiftP2)

        //Listeners
        binding.cardViewPlayer1.setOnClickListener(this)
        binding.cardViewPlayer2.setOnClickListener(this)
        binding.btnPause.setOnClickListener(this)
        binding.btnReset.setOnClickListener(this)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        loadPreferences()
        super.onResume()
    }

    private fun loadPreferences(){
        maxShifts = Math.max(PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("shifts_number", "8")!!.toInt(),1)
        minutesPerShift = Math.min(PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("shift_time", "4")!!.toLong(),59)
        player1Name = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("player1_name", getString(R.string.player1))
            .toString()
        player2Name = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("player2_name", getString(R.string.player2))
            .toString()

        binding.textViewNameP1.text = player1Name
        binding.textViewNameP2.text = player2Name

        val minutesText = if(minutesPerShift < 10) "0$minutesPerShift:00" else "$minutesPerShift:00"
        binding.textViewTimeP1.text = minutesText
        binding.textViewTimeP2.text = minutesText
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShiftsFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shift_settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shifts_settings -> {
                if(shiftP1 > 0 || shiftP2 > 0){
                    Toast.makeText(requireContext(), getString(R.string.alert_game_started), Toast.LENGTH_LONG).show()
                }else {
                    startActivity(Intent(requireContext(), SettingsActivity::class.java))
                }
                return false
            }
            else -> {}
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.cardViewPlayer1 -> changeShift(PLAYER1)
            R.id.cardViewPlayer2 -> changeShift(PLAYER2)
            R.id.btnReset -> restartGame()
            R.id.btnPause -> pauseResumeGame()
        }
    }

    private fun changeShift(player: Int) {
        binding.btnPause.isEnabled = true
        //Check if the game is finished
        if(shiftP1 == maxShifts && shiftP2 == maxShifts){
            vibratePhone(requireContext())
            val builder: AlertDialog.Builder? = AlertDialog.Builder(requireContext())
            builder?.apply {
                setPositiveButton(R.string.accept,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            }
            builder?.setMessage(R.string.game_finished)?.setTitle(R.string.app_name)
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
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

            updateShifts()
            startTimer()
        }
    }

    fun updateShifts(){
        textShiftP1.text = getString(R.string.shift) + " $shiftP1"
        textShiftP2.text = getString(R.string.shift) + " $shiftP2"
    }

    /* *******************TIMER************************ */
    fun startTimer(){
        mTimer?.let {it.cancelTimer()}
        mTimer = CustomCountDownTimer(this, minutesPerShift*60000)//in millis 1m = 60000 ms
        mTimer?.let {it.startTimer()}
    }

    private fun restartGame(){
        val builder: AlertDialog.Builder? = AlertDialog.Builder(requireContext())
        builder?.apply {
            setPositiveButton(R.string.yes,
                DialogInterface.OnClickListener { dialog, id ->
                    shiftP1 = 0
                    shiftP2 = 0
                    updateShifts()
                    textTimeP1.text = "00:00"
                    textTimeP2.text = "00:00"
                    mTimer?.let { it.cancelTimer() }
                    binding.btnPause.text = getString(R.string.pause)
                    cardViewP1.isEnabled = true
                    cardViewP2.isEnabled = true
                    dialog.dismiss()
                })
            setNegativeButton(R.string.no,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        }
        builder?.setMessage(R.string.shift_reset_dialog_message)?.setTitle(R.string.app_name)
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()




    }

    private fun pauseResumeGame(){
        mTimer?.let {
            if (it.isPaused)
                it.resumeTimer()
            else
                it.pauseTimer()
        }
    }

    override fun onCountDownPaused() {
        binding.btnPause.text = getString(R.string.resume)
        cardViewP1.isEnabled = false
        cardViewP2.isEnabled = false
    }
    override fun onCountDownResumed() {
        binding.btnPause.text = getString(R.string.pause)
        cardViewP1.isEnabled = true
        cardViewP2.isEnabled = true
    }

    override fun onCountDownChange(millisUntilFinished: Long) {
        val timeTextView = if(currentShift == PLAYER1) textTimeP1 else textTimeP2
        timeTextView.text = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
        )
    }

    override fun onCountDownFinish() {
        vibratePhone(requireContext())

        val builder: AlertDialog.Builder? = AlertDialog.Builder(requireContext())
        builder?.apply {
            setPositiveButton(R.string.accept,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                    changeShift(if(currentShift == PLAYER1) PLAYER2 else PLAYER1)
                })
        }
        builder?.setMessage(R.string.shift_finished_dialog_message)?.setTitle(R.string.app_name)
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }
}