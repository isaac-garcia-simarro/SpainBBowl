package com.isgarsi.spanbbowl.utils

import android.os.CountDownTimer
import android.widget.TextView
import java.util.concurrent.TimeUnit

class CustomCountDownTimer(val mListener: ICustomCountDownTimer, var timeInMillis: Long = 240000, val intervalInMillis: Long = 1000) {

    var timer: CountDownTimer? = null
    var isPaused: Boolean = false

    fun startTimer() {
        if(!isPaused) {
            cancelTimer()
            timer = object : CountDownTimer(timeInMillis, intervalInMillis) {
                override fun onTick(millisUntilFinished: Long) {
                    timeInMillis = millisUntilFinished
                    mListener.onCountDownChange(millisUntilFinished)
                }

                override fun onFinish() {
                    mListener.onCountDownFinish()
                }
            }
        }
        timer?.let{ it.start() }
    }

    fun pauseTimer(){
        if(!isPaused){
            isPaused = true
            timer?.let { it.cancel()}
            mListener.onCountDownPaused()
        }
    }

    fun resumeTimer(){
        if(isPaused) {
            isPaused = false
            startTimer()
            mListener.onCountDownResumed()
        }
    }

    fun cancelTimer() {
        timer?.let{ it.cancel() }
    }

    interface ICustomCountDownTimer{
        fun onCountDownPaused()
        fun onCountDownResumed()
        fun onCountDownChange(millisUntilFinished: Long);
        fun onCountDownFinish();
    }
}